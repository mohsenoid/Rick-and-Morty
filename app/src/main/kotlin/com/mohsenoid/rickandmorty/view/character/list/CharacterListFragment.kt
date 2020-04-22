package com.mohsenoid.rickandmorty.view.character.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.view.base.BaseFragment
import com.mohsenoid.rickandmorty.view.character.list.adapter.CharacterListAdapter
import kotlinx.android.synthetic.main.fragment_character_list.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class CharacterListFragment : BaseFragment(), CharacterListContract.View,
    CharacterListAdapter.ClickListener {

    private val args: CharacterListFragmentArgs by navArgs()

    private val presenter: CharacterListContract.Presenter by viewModel()

    private val adapter: CharacterListAdapter = CharacterListAdapter(listener = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(characterListFragmentModule)
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
        presenter.bind(this)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(context)
        characterList.layoutManager = linearLayoutManager
        characterList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        launch {
            presenter.loadCharacters(args.characterIds.toList())
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
        val action = CharacterListFragmentDirections
            .actionCharacterListFragmentToCharacterDetailsFragment(character.id)
        view?.findNavController()?.navigate(action)
    }

    override fun onCharacterStatusClick(character: CharacterEntity) {
        launch {
            presenter.killCharacter(character)
        }
    }

    override fun onDestroyView() {
        presenter.unbind()
        super.onDestroyView()
    }

    override fun onDestroy() {
        unloadKoinModules(characterListFragmentModule)
        super.onDestroy()
    }
}
