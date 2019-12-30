package com.mohsenoid.rickandmorty.view.episode.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.view.base.BaseFragment
import com.mohsenoid.rickandmorty.view.character.list.CharacterListActivity
import com.mohsenoid.rickandmorty.view.episode.list.adapter.EpisodeListAdapter
import com.mohsenoid.rickandmorty.view.util.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.fragment_episode_list.*
import javax.inject.Inject

class EpisodeListFragment : BaseFragment(),
    EpisodeListAdapter.ClickListener,
    OnRefreshListener {

    @Inject
    lateinit var viewModel: EpisodeListViewModel

    @Inject
    lateinit var adapter: EpisodeListAdapter

    private var endlessScrollListener: EndlessRecyclerViewScrollListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_episode_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()

        viewModel.getStateLiveData().observe(this, Observer { state ->
            when (state) {
                is EpisodeListViewModel.State.Loading.FirstPage -> showLoading()
                is EpisodeListViewModel.State.Loading.MorePage -> showLoadingMore()
                is EpisodeListViewModel.State.EndOfList -> reachedEndOfList()
                is EpisodeListViewModel.State.Offline -> showOfflineMessage()
                is EpisodeListViewModel.State.Error -> showMessage(state.errorMessage)
            }
        })

        viewModel.getEpisodesLiveData().observe(this, Observer { episodes ->
            hideLoading()
            hideLoadingMore()

            setEpisodes(episodes)
        })

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() {
        episodeListSwipeRefresh.setOnRefreshListener(this)

        val linearLayoutManager = LinearLayoutManager(context)
        episodeList.layoutManager = linearLayoutManager
        endlessScrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(
                page: Int,
                totalItemsCount: Int,
                view: RecyclerView
            ) {
                viewModel.loadMoreEpisodes(page = page)
            }
        }.also {
            episodeList.addOnScrollListener(it)
        }

        episodeList.adapter = adapter
    }

    override fun onRefresh() {
        endlessScrollListener?.resetState()
        viewModel.loadEpisodes()
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
        episodeListSwipeRefresh.isRefreshing = false
        adapter.setEpisodes(episodes)
        adapter.notifyDataSetChanged()
    }

    private fun reachedEndOfList() {
        hideLoadingMore()
    }

    override fun onEpisodeRowClick(episode: EpisodeEntity) {
        val characterListIntent: Intent =
            CharacterListActivity.newIntent(context, episode.characterIds)
        startActivity(characterListIntent)
    }

    companion object {
        fun newInstance(): EpisodeListFragment {
            return EpisodeListFragment()
        }
    }
}
