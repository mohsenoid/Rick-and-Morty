package com.mohsenoid.rickandmorty.view.episode.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.injection.DependenciesProvider
import com.mohsenoid.rickandmorty.view.base.BaseFragment
import com.mohsenoid.rickandmorty.view.character.list.CharacterListActivity
import com.mohsenoid.rickandmorty.view.episode.list.adapter.EpisodeListAdapter
import com.mohsenoid.rickandmorty.view.util.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.fragment_episode_list.*
import kotlinx.coroutines.launch

class EpisodeListFragment : BaseFragment(),
    EpisodeListContract.View,
    EpisodeListAdapter.ClickListener,
    OnRefreshListener {

    private lateinit var presenter: EpisodeListContract.Presenter
    private lateinit var adapter: EpisodeListAdapter
    private var endlessScrollListener: EndlessRecyclerViewScrollListener? = null

    public override fun injectDependencies(dependenciesProvider: DependenciesProvider) {
        presenter = dependenciesProvider.getEpisodesListFragmentPresenter()
        adapter = dependenciesProvider.getEpisodesListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_episode_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
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
                launch {
                    presenter.loadMoreEpisodes(page + 1)
                }
            }
        }.also {
            episodeList.addOnScrollListener(it)
        }

        episodeList.adapter = adapter
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
        if (!episodeListSwipeRefresh.isRefreshing) episodeListSwipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        if (episodeListSwipeRefresh.isRefreshing) episodeListSwipeRefresh.isRefreshing = false
    }

    override fun showLoadingMore() {
        episodeListProgress.visibility = View.VISIBLE
    }

    override fun hideLoadingMore() {
        episodeListProgress.visibility = View.GONE
    }

    override fun setEpisodes(episodes: List<EpisodeEntity>) {
        episodeListSwipeRefresh.isRefreshing = false
        adapter.setEpisodes(episodes)
        endlessScrollListener?.resetState()
        adapter.notifyDataSetChanged()
    }

    override fun updateEpisodes(episodes: List<EpisodeEntity>) {
        episodeListSwipeRefresh.isRefreshing = false
        adapter.addMoreEpisodes(episodes)
        adapter.notifyDataSetChanged()
    }

    override fun reachedEndOfList() {
        episodeListProgress.visibility = View.GONE
    }

    override fun onEpisodeRowClick(episode: EpisodeEntity) {
        val characterListIntent =
            CharacterListActivity.newIntent(context, episode.characterIds)
        startActivity(characterListIntent)
    }

    companion object {
        fun newInstance(): EpisodeListFragment {
            return EpisodeListFragment()
        }
    }
}
