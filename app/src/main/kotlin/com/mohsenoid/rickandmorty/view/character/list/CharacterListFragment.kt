package com.mohsenoid.rickandmorty.view.character.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.databinding.FragmentCharacterListBinding
import com.mohsenoid.rickandmorty.view.util.launchWhileResumed
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.parameter.parametersOf

@Suppress("TooManyFunctions")
class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val args: CharacterListFragmentArgs by navArgs()

    private val viewModel: CharacterListViewModel by viewModel {
        parametersOf(args.characterIds.toList())
    }

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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.launchWhileResumed {
            viewModel.onError.collectLatest {
                if (it) showErrorMessage()
            }
        }

        lifecycle.launchWhileResumed {
            viewModel.isOffline.collectLatest {
                if (it) showOfflineMessage()
            }
        }

        lifecycle.launchWhileResumed {
            viewModel.isNoCache.collectLatest {
                if (it) onNoOfflineData()
            }
        }

        lifecycle.launchWhileResumed {
            viewModel.selectedCharacterId.collectLatest { characterId ->
                onCharacterRowClick(characterId)
            }
        }
    }

    private fun showErrorMessage() {
        Toast.makeText(context, R.string.error_message, Toast.LENGTH_LONG).show()
    }

    private fun showOfflineMessage() {
        Toast.makeText(context, R.string.offline_app, Toast.LENGTH_LONG).show()
    }

    private fun onNoOfflineData() {
        Toast.makeText(context, R.string.no_offline_data, Toast.LENGTH_LONG).show()
        activity?.onBackPressed()
    }

    fun onCharacterRowClick(characterId: Int) {
        val action = CharacterListFragmentDirections
            .actionCharacterListFragmentToCharacterDetailsFragment(characterId)
        view?.findNavController()?.navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        unloadKoinModules(characterListFragmentModule)
        super.onDestroy()
    }
}
