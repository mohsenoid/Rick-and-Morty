package com.mohsenoid.rickandmorty.ui.episode.list;

import com.mohsenoid.rickandmorty.model.EpisodeModel;
import com.mohsenoid.rickandmorty.ui.base.BasePresenter;
import com.mohsenoid.rickandmorty.ui.base.BaseView;

import java.util.List;

public class EpisodeListContract {

    public interface View extends BaseView {

        void showLoadingMore();

        void hideLoadingMore();

        void setEpisodes(List<EpisodeModel> episodes);

        void updateEpisodes(List<EpisodeModel> episodes);

        void reachedEndOfList();
    }

    public interface Presenter extends BasePresenter<View> {
        void loadEpisodes();

        void loadMoreEpisodes(int page);
    }
}
