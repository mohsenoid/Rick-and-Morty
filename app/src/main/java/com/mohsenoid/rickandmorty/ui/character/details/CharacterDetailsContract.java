package com.mohsenoid.rickandmorty.ui.character.details;

import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.ui.base.BasePresenter;
import com.mohsenoid.rickandmorty.ui.base.BaseView;

public class CharacterDetailsContract {

    public interface View extends BaseView {

        void onNoOfflineData();

        void setCharacter(CharacterModel character);
    }

    public interface Presenter extends BasePresenter<View> {

        void loadCharacter(int characterId);
    }
}
