package com.mohsenoid.rickandmorty.view.character.list;

import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.view.base.BasePresenter;
import com.mohsenoid.rickandmorty.view.base.BaseView;

import java.util.List;

public interface CharacterListContract {

    interface View extends BaseView {

        void onNoOfflineData();

        void setCharacters(List<CharacterModel> characters);

        void updateCharacter(CharacterModel character);
    }

    interface Presenter extends BasePresenter<View> {

        void setCharacterIds(List<Integer> characterIds);

        void loadCharacters();

        void killCharacter(CharacterModel character);
    }
}
