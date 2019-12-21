package com.mohsenoid.rickandmorty.view.character.list;

import com.mohsenoid.rickandmorty.data.DataCallback;
import com.mohsenoid.rickandmorty.data.exception.NoOfflineDataException;
import com.mohsenoid.rickandmorty.domain.Repository;
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity;
import com.mohsenoid.rickandmorty.util.config.ConfigProvider;

import java.util.List;

public class CharacterListPresenter implements CharacterListContract.Presenter {

    private final Repository repository;
    private final ConfigProvider configProvider;

    private CharacterListContract.View view = null;
    private List<Integer> characterIds;

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
    public void setCharacterIds(List<Integer> characterIds) {
        this.characterIds = characterIds;
    }

    @Override
    public void loadCharacters() {
        if (view != null) view.showLoading();

        queryCharacters();
    }

    private void queryCharacters() {
        if (!configProvider.isOnline()) {
            if (view != null) view.showOfflineMessage(false);
        }

        repository.queryCharactersByIds(characterIds, new DataCallback<List<CharacterEntity>>() {

            @Override
            public void onSuccess(List<CharacterEntity> characters) {
                if (view != null) {
                    view.setCharacters(characters);
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

    @Override
    public void killCharacter(CharacterEntity character) {
        if (!character.isAlive()) return;

        repository.killCharacter(character.getId(), new DataCallback<CharacterEntity>() {

            @Override
            public void onSuccess(CharacterEntity character) {
                view.updateCharacter(character);
            }

            @Override
            public void onError(Exception exception) {
                if (view != null) view.showMessage(exception.getMessage());
            }
        });
    }
}
