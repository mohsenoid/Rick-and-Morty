package com.mohsenoid.rickandmorty.ui.character.list;

import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.ui.base.BasePresenter;
import com.mohsenoid.rickandmorty.ui.base.BaseView;

import java.util.List;

public class CharacterListContract {

    public interface View extends BaseView {
        void onEpisodeCharacterQueryResult(List<CharacterModel> characters);
    }

    public interface Presenter extends BasePresenter<View> {
        void queryEpisodeCharacter(int episodeId);
    }
}
