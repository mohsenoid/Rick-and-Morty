package com.mohsenoid.rickandmorty.view.character.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.databinding.FragmentCharacterListBinding
import com.mohsenoid.rickandmorty.domain.model.ModelCharacter
import com.mohsenoid.rickandmorty.view.character.list.adapter.CharacterListAdapter
import com.mohsenoid.rickandmorty.view.util.launchWhileResumed
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

@Suppress("TooManyFunctions")
class CharacterListFragment :
    Fragment(),
    CharacterListContract.View,
    CharacterListAdapter.ClickListener {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

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
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.initView()
        presenter.bind(this)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun FragmentCharacterListBinding.initView() {
        val linearLayoutManager = LinearLayoutManager(context)
        characterList.layoutManager = linearLayoutManager
        characterList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        lifecycle.launchWhileResumed {
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
        binding.characterListProgress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.characterListProgress.visibility = View.GONE
    }

    override fun onNoOfflineData() {
        Toast.makeText(context, R.string.no_offline_data, Toast.LENGTH_LONG).show()
        activity?.onBackPressed()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setCharacters(characters: List<ModelCharacter>) {
        adapter.setCharacters(characters)
        adapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateCharacter(character: ModelCharacter) {
        adapter.updateCharacter(character)
        adapter.notifyDataSetChanged()
    }

    override fun onCharacterRowClick(character: ModelCharacter) {
        val action = CharacterListFragmentDirections
            .actionCharacterListFragmentToCharacterDetailsFragment(character.id)
        view?.findNavController()?.navigate(action)
    }

    override fun onCharacterStatusClick(character: ModelCharacter) {
        lifecycle.launchWhileResumed {
            presenter.killCharacter(character)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unbind()
        _binding = null
    }

    override fun onDestroy() {
        unloadKoinModules(characterListFragmentModule)
        super.onDestroy()
    }
}
