package com.mohsenoid.rickandmorty.view.episode.list;

import com.mohsenoid.rickandmorty.data.DataCallback;
import com.mohsenoid.rickandmorty.data.exception.EndOfListException;
import com.mohsenoid.rickandmorty.domain.Repository;
import com.mohsenoid.rickandmorty.model.EpisodeModel;
import com.mohsenoid.rickandmorty.util.config.ConfigProvider;

import java.util.List;

public class EpisodeListPresenter implements EpisodeListContract.Presenter {

    private final Repository repository;
    private final ConfigProvider configProvider;

    private EpisodeListContract.View view = null;

    private int page = 1;

    public EpisodeListPresenter(Repository repository, ConfigProvider configProvider) {
        this.repository = repository;
        this.configProvider = configProvider;
    }

    @Override
    public void bind(EpisodeListContract.View view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        view = null;
    }

    @Override
    public void loadEpisodes() {
        if (view != null) view.showLoading();

        page = 1;
        queryEpisodes();
    }

    @Override
    public void loadMoreEpisodes(int page) {
        if (view != null) view.showLoadingMore();

        this.page = page;
        queryEpisodes();
    }

    private void queryEpisodes() {
        if (!configProvider.isOnline()) {
            if (view != null) view.showOfflineMessage(false);
        }

        repository.queryEpisodes(page, new DataCallback<List<EpisodeModel>>() {

            @Override
            public void onSuccess(List<EpisodeModel> episodes) {
                if (view != null) {
                    if (page == 1) {
                        view.setEpisodes(episodes);
                        view.hideLoading();
                    } else {
                        view.updateEpisodes(episodes);
                        view.hideLoadingMore();
                    }
                }
            }

            @Override
            public void onError(Exception exception) {
                if (view != null) {
                    view.hideLoading();
                    view.hideLoadingMore();
                }

                if (exception instanceof EndOfListException) {
                    if (view != null) view.reachedEndOfList();
                } else {
                    if (view != null) view.showMessage(exception.getMessage());
                }
            }
        });
    }
}
