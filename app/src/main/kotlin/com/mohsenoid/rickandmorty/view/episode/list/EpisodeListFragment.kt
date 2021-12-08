package com.mohsenoid.rickandmorty.view.episode.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.mohsenoid.rickandmorty.view.util.launchWhileResumed
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class EpisodeListFragment : Fragment() {

    private val viewModel: EpisodeListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(episodeListFragmentModule)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                EpisodeListScreen(viewModel)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.launchWhileResumed {
            viewModel.selectedEpisodeCharacterIds.collectLatest { characterIds ->
                onEpisodeRowClick(characterIds)
            }
        }
    }

    private fun onEpisodeRowClick(characterIds: IntArray) {
        val action = EpisodeListFragmentDirections
            .actionEpisodeListFragmentToCharacterListFragment(characterIds)
        view?.findNavController()?.navigate(action)
    }

    override fun onDestroy() {
        unloadKoinModules(episodeListFragmentModule)
        super.onDestroy()
    }
}
