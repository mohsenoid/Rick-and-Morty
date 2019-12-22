package com.mohsenoid.rickandmorty.view.character.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.injection.DependenciesProvider
import com.mohsenoid.rickandmorty.view.base.BaseFragment
import com.mohsenoid.rickandmorty.view.character.details.CharacterDetailsActivity
import com.mohsenoid.rickandmorty.view.character.list.adapter.CharacterListAdapter
import kotlinx.android.synthetic.main.fragment_character_list.*
import java.util.*

class CharacterListFragment : BaseFragment(), CharacterListContract.View,
    CharacterListAdapter.ClickListener {

    private lateinit var presenter: CharacterListContract.Presenter

    private lateinit var adapter: CharacterListAdapter

    public override fun injectDependencies(dependenciesProvider: DependenciesProvider) {
        presenter = dependenciesProvider.getCharacterListFragmentPresenter()
        adapter = dependenciesProvider.getCharacterListAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val characterIds = extractCharacterIds()

        if (characterIds == null) {
            Toast.makeText(context, "Character ids are missing!", Toast.LENGTH_SHORT).show()
            activity?.onBackPressed()
        } else {
            presenter.characterIds = characterIds
        }
    }

    private fun extractCharacterIds(): ArrayList<Int>? {
        val args = arguments ?: return null
        return args.getIntegerArrayList(ARG_CHARACTER_IDS)
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
        presenter.loadCharacters()
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
        val characterDetailsIntent =
            CharacterDetailsActivity.newIntent(context, character.id)
        startActivity(characterDetailsIntent)
    }

    override fun onCharacterStatusClick(character: CharacterEntity) {
        presenter.killCharacter(character)
    }

    companion object {

        private const val ARG_CHARACTER_IDS = "character_ids"

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
