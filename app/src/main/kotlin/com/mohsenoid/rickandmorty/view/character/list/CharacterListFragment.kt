package com.mohsenoid.rickandmorty.view.character.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.view.base.BaseFragment
import com.mohsenoid.rickandmorty.view.character.details.CharacterDetailsActivity
import com.mohsenoid.rickandmorty.view.character.list.adapter.CharacterListAdapter
import kotlinx.android.synthetic.main.fragment_character_list.*
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Named

class CharacterListFragment : BaseFragment(), CharacterListContract.View,
    CharacterListAdapter.ClickListener {

    @JvmField
    @field:[Inject Named(ARG_CHARACTER_IDS)]
    var characterIds: List<Int>? = null

    @Inject
    lateinit var presenter: CharacterListContract.Presenter

    @Inject
    lateinit var adapter: CharacterListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        characterIds.let { characterIds ->
            if (characterIds == null) {
                Toast.makeText(context, "Character ids are missing!", Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            } else {
                presenter.characterIds = characterIds
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(context)
        characterList.layoutManager = linearLayoutManager
        characterList.adapter = adapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter.bind(this)
    }

    override fun onDetach() {
        super.onDetach()
        presenter.unbind()
    }

    override fun onResume() {
        super.onResume()
        launch {
            presenter.loadCharacters()
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showOfflineMessage(isCritical: Boolean) {
        Toast.makeText(context, R.string.offline_app, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        characterListProgress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        characterListProgress.visibility = View.GONE
    }

    override fun onNoOfflineData() {
        Toast.makeText(context, R.string.no_offline_data, Toast.LENGTH_LONG).show()
        activity?.onBackPressed()
    }

    override fun setCharacters(characters: List<CharacterEntity>) {
        adapter.setCharacters(characters)
        adapter.notifyDataSetChanged()
    }

    override fun updateCharacter(character: CharacterEntity) {
        adapter.updateCharacter(character)
        adapter.notifyDataSetChanged()
    }

    override fun onCharacterRowClick(character: CharacterEntity) {
        val characterDetailsIntent: Intent =
            CharacterDetailsActivity.newIntent(context, character.id)
        startActivity(characterDetailsIntent)
    }

    override fun onCharacterStatusClick(character: CharacterEntity) {
        launch {
            presenter.killCharacter(character)
        }
    }

    companion object {

        const val ARG_CHARACTER_IDS: String = "character_ids"

        fun newInstance(characterIds: List<Int>): CharacterListFragment {
            val fragment = CharacterListFragment()
            fragment.arguments = Bundle().apply {
                putIntegerArrayList(
                    ARG_CHARACTER_IDS,
                    ArrayList(characterIds)
                )
            }
            return fragment
        }
    }
}
