package com.mohsenoid.rickandmorty.ui.character.list;

import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.ui.base.BasePresenter;
import com.mohsenoid.rickandmorty.ui.base.BaseView;

import java.util.List;

public class CharacterListContract {

    public interface View extends BaseView {

        void onNoOfflineData();

        void setCharacters(List<CharacterModel> characters);

        void updateCharacter(CharacterModel character);
    }

    public interface Presenter extends BasePresenter<View> {

        void loadCharacters(List<Integer> characterIds);

        void killCharacter(CharacterModel character);
    }
}
