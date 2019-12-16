package com.mohsenoid.rickandmorty.view.episode.list;

import com.mohsenoid.rickandmorty.model.EpisodeModel;
import com.mohsenoid.rickandmorty.view.base.BasePresenter;
import com.mohsenoid.rickandmorty.view.base.BaseView;

import java.util.List;

public interface EpisodeListContract {

    interface View extends BaseView {

        void showLoadingMore();

        void hideLoadingMore();

        void setEpisodes(List<EpisodeModel> episodes);

        void updateEpisodes(List<EpisodeModel> episodes);

        void reachedEndOfList();
    }

    interface Presenter extends BasePresenter<View> {

        void loadEpisodes();

        void loadMoreEpisodes(int page);
    }
}
