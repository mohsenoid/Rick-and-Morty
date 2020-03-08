package com.mohsenoid.rickandmorty.view.character.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
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

class CharacterListFragment : BaseFragment(), CharacterListAdapter.ClickListener {

    @JvmField
    @field:[Inject Named(ARG_CHARACTER_IDS)]
    var characterIds: List<Int>? = null

    @Inject
    lateinit var viewModel: CharacterListViewModel

    @Inject
    lateinit var adapter: CharacterListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        characterIds.let { characterIds ->
            if (characterIds == null) {
                Toast.makeText(context, "Character ids are missing!", Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            } else {
                viewModel.characterIds = characterIds
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.getStateLiveData().observe(this, Observer { state ->
            when (state) {
                is CharacterListViewModel.State.Loading -> showLoading()
                is CharacterListViewModel.State.NoOfflineData -> onNoOfflineData()
                is CharacterListViewModel.State.Offline -> showOfflineMessage()
                is CharacterListViewModel.State.Error -> showMessage(state.errorMessage)
            }
        })

        viewModel.getCharactersLiveData().observe(this, Observer { characters ->
            hideLoading()
            setCharacters(characters)
        })

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

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun showOfflineMessage() {
        Toast.makeText(context, R.string.offline_app, Toast.LENGTH_LONG).show()
    }

    private fun showLoading() {
        characterListProgress.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        characterListProgress.visibility = View.GONE
    }

    private fun onNoOfflineData() {
        Toast.makeText(context, R.string.no_offline_data, Toast.LENGTH_LONG).show()
        activity?.onBackPressed()
    }

    private fun setCharacters(characters: List<CharacterEntity>) {
        adapter.setCharacters(characters)
        adapter.notifyDataSetChanged()
    }

    override fun onCharacterRowClick(character: CharacterEntity) {
        val characterDetailsIntent: Intent =
            CharacterDetailsActivity.newIntent(context, character.id)
        startActivity(characterDetailsIntent)
    }

    override fun onCharacterStatusClick(character: CharacterEntity) {
        launch {
            viewModel.killCharacter(character)
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
