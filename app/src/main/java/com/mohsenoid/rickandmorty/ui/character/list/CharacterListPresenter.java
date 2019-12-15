package com.mohsenoid.rickandmorty.ui.character.list;

import com.mohsenoid.rickandmorty.config.ConfigProvider;
import com.mohsenoid.rickandmorty.data.DataCallback;
import com.mohsenoid.rickandmorty.data.Repository;
import com.mohsenoid.rickandmorty.model.CharacterModel;

import java.util.List;

public class CharacterListPresenter implements CharacterListContract.Presenter {

    private CharacterListContract.View view = null;

    private Repository repository;
    private ConfigProvider configProvider;

    public CharacterListPresenter(Repository repository, ConfigProvider configProvider) {
        this.repository = repository;
        this.configProvider = configProvider;
    }

    @Override
    public void bind(CharacterListContract.View view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        view = null;
    }

    @Override
    public void loadCharacters(List<Integer> characterIds) {
        if (view != null) view.showLoading();

        queryCharacters(characterIds);
    }

    private void queryCharacters(List<Integer> characterIds) {
        if (!configProvider.isOnline()) {
            if (view != null) view.showOfflineMessage(false);
        }

        repository.queryCharacters(characterIds, new DataCallback<List<CharacterModel>>() {

            @Override
            public void onSuccess(List<CharacterModel> characters) {
                if (view != null) {
                    view.onCharactersQueryResult(characterIds, characters);
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
