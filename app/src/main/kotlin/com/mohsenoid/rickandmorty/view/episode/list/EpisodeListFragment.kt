package com.mohsenoid.rickandmorty.view.episode.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.view.base.BaseFragment
import com.mohsenoid.rickandmorty.view.episode.list.adapter.EpisodeListAdapter
import com.mohsenoid.rickandmorty.view.util.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.fragment_episode_list.*
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class EpisodeListFragment : BaseFragment(),
    EpisodeListAdapter.ClickListener,
    OnRefreshListener {

    private val listViewModel: EpisodeListViewModel by viewModel()

    private val adapter: EpisodeListAdapter = EpisodeListAdapter(listener = this)

    private lateinit var endlessScrollListener: EndlessRecyclerViewScrollListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_episode_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()

        listViewModel.loadingStatus.observe(viewLifecycleOwner, Observer(::onLoadingStatusUpdate))
        listViewModel.episodes.observe(viewLifecycleOwner, Observer(::setEpisodes))
        listViewModel.loadEpisodes()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() {
        episodeListSwipeRefresh.setOnRefreshListener(this)

        val linearLayoutManager = LinearLayoutManager(context)
        episodeList.layoutManager = linearLayoutManager
        initEndlessScrollListener(linearLayoutManager)

        episodeList.adapter = adapter
    }

    private fun initEndlessScrollListener(linearLayoutManager: LinearLayoutManager) {
        endlessScrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(
                page: Int,
                totalItemsCount: Int,
                view: RecyclerView
            ) {
                launch {
                    listViewModel.loadMoreEpisodes(page = page + 1)
                }
            }
        }.also {
            episodeList.addOnScrollListener(it)
        }
    }

    private fun onLoadingStatusUpdate(status: EpisodeListViewModel.LoadingStatus) {
        when (status) {
            EpisodeListViewModel.LoadingStatus.LoadSuccessful -> {
                hideLoading()
                endlessScrollListener.resetState()
            }
            EpisodeListViewModel.LoadingStatus.LoadMoreSuccessful -> {
                hideLoadingMore()
            }
            EpisodeListViewModel.LoadingStatus.Loading -> {
                showLoading()
            }
            EpisodeListViewModel.LoadingStatus.LoadingMore -> {
                showLoadingMore()
            }
            EpisodeListViewModel.LoadingStatus.ReachedEndOfList -> {
                reachedEndOfList()
            }
            EpisodeListViewModel.LoadingStatus.Offline -> {
                showOfflineMessage()
            }
            is EpisodeListViewModel.LoadingStatus.Failed -> {
                showMessage(status.throwable.message ?: "Unknown Error! ${status.throwable}")
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun showOfflineMessage() {
        Toast.makeText(context, R.string.offline_app, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        if (!episodeListSwipeRefresh.isRefreshing) episodeListSwipeRefresh.isRefreshing = true
    }

    private fun hideLoading() {
        if (episodeListSwipeRefresh.isRefreshing) episodeListSwipeRefresh.isRefreshing = false
    }

    private fun showLoadingMore() {
        episodeListProgress.visibility = View.VISIBLE
    }

    private fun hideLoadingMore() {
        episodeListProgress.visibility = View.GONE
    }

    private fun setEpisodes(episodes: List<EpisodeEntity>) {
        adapter.setEpisodes(episodes)
        adapter.notifyDataSetChanged()
    }

    private fun reachedEndOfList() {
        episodeListProgress.visibility = View.GONE
    }

    override fun onRefresh() {
        launch {
            listViewModel.loadEpisodes()
        }
    }

    override fun onEpisodeRowClick(episode: EpisodeEntity) {
        val action = EpisodeListFragmentDirections
            .actionEpisodeListFragmentToCharacterListFragment(episode.characterIds.toIntArray())
        view?.findNavController()?.navigate(action)
    }
}
