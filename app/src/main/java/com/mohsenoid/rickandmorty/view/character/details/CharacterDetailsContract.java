package com.mohsenoid.rickandmorty.view.character.details;

import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity;
import com.mohsenoid.rickandmorty.view.base.BasePresenter;
import com.mohsenoid.rickandmorty.view.base.BaseView;

public interface CharacterDetailsContract {

    interface View extends BaseView {

        void onNoOfflineData();

        void setCharacter(CharacterEntity character);
    }

    interface Presenter extends BasePresenter<View> {

        void setCharacterId(int characterId);

        void loadCharacter();
    }
}
