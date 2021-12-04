package com.mohsenoid.rickandmorty.view.episode.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.view.util.EndlessRecyclerViewScrollListener
import com.mohsenoid.rickandmorty.view.util.launchWhileResumed
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

@Suppress("TooManyFunctions")
class EpisodeListFragment : Fragment() {

    // private var _binding: FragmentEpisodeListBinding? = null
    // private val binding get() = _binding!!

    private val viewModel: EpisodeListViewModel by viewModel()

    private var endlessScrollListener: EndlessRecyclerViewScrollListener? = null

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

        val linearLayoutManager = LinearLayoutManager(context)
        // binding.episodeList.layoutManager = linearLayoutManager
        endlessScrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                viewModel.loadMoreEpisodes(page = page + 1)
            }
        }.also {
            // binding.episodeList.addOnScrollListener(it)
        }

        // lifecycle.launchWhileResumed {
        //     viewModel.onError.collectLatest {
        //         if (it) showErrorMessage()
        //     }
        // }

        // lifecycle.launchWhileResumed {
        //     viewModel.isOffline.collectLatest {
        //         if (it) showOfflineMessage()
        //     }
        // }

        // lifecycle.launchWhileResumed {
        //     viewModel.isEndOfList.collectLatest {
        //         if (it) reachedEndOfList()
        //     }
        // }

        lifecycle.launchWhileResumed {
            viewModel.selectedEpisodeCharacterIds.collectLatest { characterIds ->
                onEpisodeRowClick(characterIds)
            }
        }
    }

    private fun showErrorMessage() {
        Toast.makeText(context, R.string.error_message, Toast.LENGTH_LONG).show()
    }

    private fun showOfflineMessage() {
        Toast.makeText(context, R.string.offline_app, Toast.LENGTH_SHORT).show()
    }

    private fun reachedEndOfList() {
        // binding.episodeListProgress.visibility = View.GONE
    }

    private fun onEpisodeRowClick(characterIds: IntArray) {
        val action = EpisodeListFragmentDirections
            .actionEpisodeListFragmentToCharacterListFragment(characterIds)
        view?.findNavController()?.navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        endlessScrollListener = null
        // _binding = null
    }

    override fun onDestroy() {
        unloadKoinModules(episodeListFragmentModule)
        super.onDestroy()
    }
}
