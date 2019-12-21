package com.mohsenoid.rickandmorty.view.episode.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mohsenoid.rickandmorty.R;
import com.mohsenoid.rickandmorty.injection.DependenciesProvider;
import com.mohsenoid.rickandmorty.model.EpisodeModel;
import com.mohsenoid.rickandmorty.view.base.BaseFragment;
import com.mohsenoid.rickandmorty.view.character.list.CharacterListActivity;
import com.mohsenoid.rickandmorty.view.episode.list.adapter.EpisodeListAdapter;
import com.mohsenoid.rickandmorty.view.util.EndlessRecyclerViewScrollListener;

import java.util.List;

public class EpisodeListFragment extends BaseFragment implements EpisodeListContract.View, EpisodeListAdapter.ClickListener, SwipeRefreshLayout.OnRefreshListener {

    private EpisodeListContract.Presenter presenter;
    private SwipeRefreshLayout swipeRefresh;
    private ProgressBar progress;
    private EndlessRecyclerViewScrollListener scrollListener;
    private EpisodeListAdapter adapter;

    public static EpisodeListFragment newInstance() {
        return new EpisodeListFragment();
    }

    @Override
    public void injectDependencies(DependenciesProvider dependenciesProvider) {
        presenter = dependenciesProvider.getEpisodesListFragmentPresenter();
        adapter = dependenciesProvider.getEpisodesListAdapter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_episode_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        swipeRefresh = view.findViewById(R.id.episode_list_swipe_refresh);
        swipeRefresh.setOnRefreshListener(this);

        progress = view.findViewById(R.id.episode_list_progress);

        RecyclerView episodeList = view.findViewById(R.id.episode_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        episodeList.setLayoutManager(linearLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                presenter.loadMoreEpisodes(page + 1);
            }
        };

        episodeList.addOnScrollListener(scrollListener);
        episodeList.setAdapter(adapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter.bind(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.loadEpisodes();
    }

    @Override
    public void onRefresh() {
        presenter.loadEpisodes();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showOfflineMessage(boolean isCritical) {
        Toast.makeText(getContext(), R.string.offline_app, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        if (!swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showLoadingMore() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingMore() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void setEpisodes(List<EpisodeModel> episodes) {
        swipeRefresh.setRefreshing(false);

        adapter.setEpisodes(episodes);
        scrollListener.resetState();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateEpisodes(List<EpisodeModel> episodes) {
        swipeRefresh.setRefreshing(false);

        adapter.addMoreEpisodes(episodes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void reachedEndOfList() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onEpisodeRowClick(EpisodeModel episode) {
        Intent characterListIntent = CharacterListActivity.newIntent(getContext(), episode.getCharacterIds());
        startActivity(characterListIntent);
    }
}
