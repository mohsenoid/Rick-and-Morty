package com.mohsenoid.rickandmorty.ui.character.details;

import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.ui.base.BasePresenter;
import com.mohsenoid.rickandmorty.ui.base.BaseView;

public class CharacterDetailsContract {

    public interface View extends BaseView {
        void onCharacterQueryResult(int characterId, CharacterModel character);
    }

    public interface Presenter extends BasePresenter<View> {
        void loadCharacter(int characterId);
    }
}
