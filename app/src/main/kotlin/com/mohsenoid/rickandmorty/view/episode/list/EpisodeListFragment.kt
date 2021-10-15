package com.mohsenoid.rickandmorty.view.episode.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.databinding.FragmentEpisodeListBinding
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.view.base.BaseFragment
import com.mohsenoid.rickandmorty.view.episode.list.adapter.EpisodeListAdapter
import com.mohsenoid.rickandmorty.view.util.EndlessRecyclerViewScrollListener
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

@Suppress("TooManyFunctions")
class EpisodeListFragment :
    BaseFragment(),
    EpisodeListContract.View,
    EpisodeListAdapter.ClickListener,
    OnRefreshListener {

    private var _binding: FragmentEpisodeListBinding? = null
    private val binding get() = _binding!!

    private val presenter: EpisodeListContract.Presenter by viewModel()

    private val adapter: EpisodeListAdapter = EpisodeListAdapter(listener = this)

    private var endlessScrollListener: EndlessRecyclerViewScrollListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(episodeListFragmentModule)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.initView()
        presenter.bind(this)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun FragmentEpisodeListBinding.initView() {
        episodeListSwipeRefresh.setOnRefreshListener(this@EpisodeListFragment)

        val linearLayoutManager = LinearLayoutManager(context)
        episodeList.layoutManager = linearLayoutManager
        endlessScrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(
                page: Int,
                totalItemsCount: Int,
                view: RecyclerView
            ) {
                launch {
                    presenter.loadMoreEpisodes(page = page + 1)
                }
            }
        }.also {
            episodeList.addOnScrollListener(it)
        }

        episodeList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        launch {
            presenter.loadEpisodes()
        }
    }

    override fun onRefresh() {
        launch {
            presenter.loadEpisodes()
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showOfflineMessage(isCritical: Boolean) {
        Toast.makeText(context, R.string.offline_app, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        with(binding) {
            if (!episodeListSwipeRefresh.isRefreshing) episodeListSwipeRefresh.isRefreshing = true
        }
    }

    override fun hideLoading() {
        with(binding) {
            if (episodeListSwipeRefresh.isRefreshing) episodeListSwipeRefresh.isRefreshing = false
        }
    }

    override fun showLoadingMore() {
        binding.episodeListProgress.visibility = View.VISIBLE
    }

    override fun hideLoadingMore() {
        binding.episodeListProgress.visibility = View.GONE
    }

    override fun setEpisodes(episodes: List<EpisodeEntity>) {
        binding.episodeListSwipeRefresh.isRefreshing = false
        adapter.setEpisodes(episodes)
        endlessScrollListener?.resetState()
        adapter.notifyDataSetChanged()
    }

    override fun updateEpisodes(episodes: List<EpisodeEntity>) {
        binding.episodeListSwipeRefresh.isRefreshing = false
        adapter.addMoreEpisodes(episodes)
        adapter.notifyDataSetChanged()
    }

    override fun reachedEndOfList() {
        binding.episodeListProgress.visibility = View.GONE
    }

    override fun onEpisodeRowClick(episode: EpisodeEntity) {
        val action = EpisodeListFragmentDirections
            .actionEpisodeListFragmentToCharacterListFragment(episode.characterIds.toIntArray())
        view?.findNavController()?.navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unbind()
        _binding = null
    }

    override fun onDestroy() {
        unloadKoinModules(episodeListFragmentModule)
        super.onDestroy()
    }

    companion object {
        fun newInstance(): EpisodeListFragment {
            return EpisodeListFragment()
        }
    }
}
