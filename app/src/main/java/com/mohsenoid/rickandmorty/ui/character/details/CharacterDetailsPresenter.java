package com.mohsenoid.rickandmorty.ui.character.details;

import com.mohsenoid.rickandmorty.config.ConfigProvider;
import com.mohsenoid.rickandmorty.data.DataCallback;
import com.mohsenoid.rickandmorty.data.Repository;
import com.mohsenoid.rickandmorty.model.CharacterModel;


public class CharacterDetailsPresenter implements CharacterDetailsContract.Presenter {

    private CharacterDetailsContract.View view = null;

    private Repository repository;
    private ConfigProvider configProvider;

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
    public void loadCharacter(int characterId) {
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
                    view.onCharacterQueryResult(characterId, character);
                    view.hideLoading();
                }
            }

            @Override
            public void onError(Exception exception) {
                if (view != null) {
                    view.hideLoading();
                }

                if (view != null) view.showMessage(exception.getMessage());
            }
        });
    }
}
