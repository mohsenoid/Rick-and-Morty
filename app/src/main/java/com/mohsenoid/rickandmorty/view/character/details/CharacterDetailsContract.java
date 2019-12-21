package com.mohsenoid.rickandmorty.view.character.details;

import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.view.base.BasePresenter;
import com.mohsenoid.rickandmorty.view.base.BaseView;

public interface CharacterDetailsContract {

    interface View extends BaseView {

        void onNoOfflineData();

        void setCharacter(CharacterModel character);
    }

    interface Presenter extends BasePresenter<View> {

        void setCharacterId(int characterId);

        void loadCharacter();
    }
}
