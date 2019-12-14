package com.mohsenoid.rickandmorty.data;

import com.mohsenoid.rickandmorty.config.ConfigProvider;
import com.mohsenoid.rickandmorty.data.db.Datastore;
import com.mohsenoid.rickandmorty.data.exception.EndOfListException;
import com.mohsenoid.rickandmorty.data.exception.NoOfflineDataException;
import com.mohsenoid.rickandmorty.data.service.ApiClient;
import com.mohsenoid.rickandmorty.executor.TaskExecutor;
import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.model.EpisodeModel;

import java.util.List;

public class RepositoryImpl implements Repository {

    private Datastore datastore;
    private ApiClient apiClient;
    private TaskExecutor taskExecutor;
    private ConfigProvider configProvider;

    public RepositoryImpl(Datastore datastore, ApiClient apiClient, TaskExecutor taskExecutor, ConfigProvider configProvider) {
        this.datastore = datastore;
        this.apiClient = apiClient;
        this.taskExecutor = taskExecutor;
        this.configProvider = configProvider;
    }

    @Override
    public void queryEpisodes(int page, DataCallback<List<EpisodeModel>> callback) {
        if (configProvider.isOnline()) {
            queryEpisodesApi(page, callback);
        } else {
            queryEpisodesDb(page, callback);
        }
    }

    private void queryEpisodesApi(int page, DataCallback<List<EpisodeModel>> callback) {
        taskExecutor.execute(() -> {
            try {
                List<EpisodeModel> episodes = apiClient.getEpisodes(page);

                for (EpisodeModel episode : episodes) {
                    datastore.insertEpisode(episode);
                }

                if (callback != null) callback.onSuccess(episodes);
            } catch (Exception e) {
                e.printStackTrace();

                queryEpisodesDb(page, callback);
            }
        });
    }

    private void queryEpisodesDb(int page, DataCallback<List<EpisodeModel>> callback) {
        taskExecutor.execute(() -> {
            try {
                List<EpisodeModel> episodes = datastore.queryAllEpisodes(page);

                if (episodes.size() > 0) {
                    if (callback != null) callback.onSuccess(episodes);
                } else {
                    if (callback != null) callback.onError(new EndOfListException());
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (callback != null) callback.onError(e);
            }
        });
    }

    @Override
    public void queryCharacters(int page, DataCallback<List<CharacterModel>> callback) {
        if (configProvider.isOnline()) {
            queryCharactersApi(page, callback);
        } else {
            queryCharactersDb(page, callback);
        }
    }

    private void queryCharactersApi(int page, DataCallback<List<CharacterModel>> callback) {
        taskExecutor.execute(() -> {
            try {
                List<CharacterModel> characters = apiClient.getCharacters(page);

                for (CharacterModel character : characters) {
                    datastore.insertCharacter(character);
                }

                if (callback != null) callback.onSuccess(characters);
            } catch (Exception e) {
                e.printStackTrace();

                queryCharactersDb(page, callback);
            }
        });
    }

    private void queryCharactersDb(int page, DataCallback<List<CharacterModel>> callback) {
        taskExecutor.execute(() -> {
            try {
                List<CharacterModel> characters = datastore.queryAllCharacters(page);

                if (characters.size() > 0) {
                    if (callback != null) callback.onSuccess(characters);
                } else {
                    if (callback != null) callback.onError(new EndOfListException());
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (callback != null) callback.onError(e);
            }
        });
    }

    @Override
    public void queryCharacterDetails(int characterId, DataCallback<CharacterModel> callback) {
        if (configProvider.isOnline()) {
            queryCharacterDetailsApi(characterId, callback);
        } else {
            queryCharacterDetailsDb(characterId, callback);
        }
    }

    private void queryCharacterDetailsApi(int characterId, DataCallback<CharacterModel> callback) {
        taskExecutor.execute(() -> {
            try {
                CharacterModel character = apiClient.getCharacterDetails(characterId);

                datastore.insertCharacter(character);

                if (callback != null) callback.onSuccess(character);
            } catch (Exception e) {
                e.printStackTrace();

                queryCharacterDetailsDb(characterId, callback);
            }
        });
    }

    private void queryCharacterDetailsDb(int characterId, DataCallback<CharacterModel> callback) {
        taskExecutor.execute(() -> {
            try {
                CharacterModel character = datastore.queryCharacter(characterId);

                if (character != null) {
                    if (callback != null) callback.onSuccess(character);
                } else {
                    if (callback != null) callback.onError(new NoOfflineDataException());
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (callback != null) callback.onError(e);
            }
        });
    }
}
