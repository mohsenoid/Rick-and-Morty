package com.mohsenoid.rickandmorty.ui.character.details;

import com.mohsenoid.rickandmorty.config.ConfigProvider;
import com.mohsenoid.rickandmorty.data.DataCallback;
import com.mohsenoid.rickandmorty.data.Repository;
import com.mohsenoid.rickandmorty.data.exception.NoOfflineDataException;
import com.mohsenoid.rickandmorty.model.CharacterModel;


public class CharacterDetailsPresenter implements CharacterDetailsContract.Presenter {

    private final Repository repository;
    private final ConfigProvider configProvider;

    private int characterId;

    private CharacterDetailsContract.View view = null;

    public CharacterDetailsPresenter(Repository repository, ConfigProvider configProvider) {
        this.repository = repository;
        this.configProvider = configProvider;
    }

    @Override
    public void bind(CharacterDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        view = null;
    }

    @Override
    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    @Override
    public void loadCharacter() {
        if (view != null) view.showLoading();

        queryCharacter(characterId);
    }

    private void queryCharacter(int characterId) {
        if (!configProvider.isOnline()) {
            if (view != null) view.showOfflineMessage(false);
        }

        repository.queryCharacterDetails(characterId, new DataCallback<CharacterModel>() {

            @Override
            public void onSuccess(CharacterModel character) {
                if (view != null) {
                    view.setCharacter(character);
                    view.hideLoading();
                }
            }

            @Override
            public void onError(Exception exception) {
                if (view != null) {
                    view.hideLoading();
                }

                if (exception instanceof NoOfflineDataException) {
                    if (view != null) view.onNoOfflineData();
                } else {
                    if (view != null) view.showMessage(exception.getMessage());
                }
            }
        });
    }
}
