package com.mohsenoid.rickandmorty.data;

import androidx.annotation.VisibleForTesting;

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

    @VisibleForTesting
    public static RepositoryImpl instance;

    private final Datastore datastore;
    private final ApiClient apiClient;
    private final TaskExecutor ioTaskExecutor;
    private final TaskExecutor mainTaskExecutor;
    private final ConfigProvider configProvider;

    private RepositoryImpl(Datastore datastore, ApiClient apiClient, TaskExecutor ioTaskExecutor, TaskExecutor mainTaskExecutor, ConfigProvider configProvider) {
        this.datastore = datastore;
        this.apiClient = apiClient;
        this.ioTaskExecutor = ioTaskExecutor;
        this.mainTaskExecutor = mainTaskExecutor;
        this.configProvider = configProvider;
    }

    public static synchronized RepositoryImpl getInstance(Datastore datastore, ApiClient apiClient, TaskExecutor ioTaskExecutor, TaskExecutor mainTaskExecutor, ConfigProvider configProvider) {
        if (instance == null)
            instance = new RepositoryImpl(datastore, apiClient, ioTaskExecutor, mainTaskExecutor, configProvider);

        return instance;
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
        ioTaskExecutor.execute(() -> {
            try {
                List<EpisodeModel> episodes = apiClient.getEpisodes(page);

                for (EpisodeModel episode : episodes) {
                    datastore.insertEpisode(episode);
                }

                queryEpisodesDb(page, callback);
            } catch (Exception e) {
                e.printStackTrace();

                queryEpisodesDb(page, callback);
            }
        });
    }

    private void queryEpisodesDb(int page, DataCallback<List<EpisodeModel>> callback) {
        ioTaskExecutor.execute(() -> {
            try {
                List<EpisodeModel> episodes = datastore.queryAllEpisodes(page);

                mainTaskExecutor.execute(() -> {
                    if (episodes.size() > 0) {
                        if (callback != null) callback.onSuccess(episodes);
                    } else {
                        if (callback != null) callback.onError(new EndOfListException());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                mainTaskExecutor.execute(() -> {
                    if (callback != null) callback.onError(e);
                });
            }
        });
    }

    @Override
    public void queryCharacters(List<Integer> characterIds, DataCallback<List<CharacterModel>> callback) {
        if (configProvider.isOnline()) {
            queryCharactersApi(characterIds, callback);
        } else {
            queryCharactersDb(characterIds, callback);
        }
    }

    private void queryCharactersApi(List<Integer> characterIds, DataCallback<List<CharacterModel>> callback) {
        ioTaskExecutor.execute(() -> {
            try {
                List<CharacterModel> characters = apiClient.getCharacters(characterIds);

                for (CharacterModel character : characters) {
                    datastore.insertCharacter(character);
                }

                queryCharactersDb(characterIds, callback);
            } catch (Exception e) {
                e.printStackTrace();

                queryCharactersDb(characterIds, callback);
            }
        });
    }

    private void queryCharactersDb(List<Integer> characterIds, DataCallback<List<CharacterModel>> callback) {
        ioTaskExecutor.execute(() -> {
            try {
                List<CharacterModel> characters = datastore.queryAllCharacters(characterIds);

                mainTaskExecutor.execute(() -> {
                    if (characters.size() > 0) {
                        if (callback != null) callback.onSuccess(characters);
                    } else {
                        if (callback != null) callback.onError(new NoOfflineDataException());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                mainTaskExecutor.execute(() -> {
                    if (callback != null) callback.onError(e);
                });
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
        ioTaskExecutor.execute(() -> {
            try {
                CharacterModel character = apiClient.getCharacterDetails(characterId);

                datastore.insertCharacter(character);

                queryCharacterDetailsDb(characterId, callback);
            } catch (Exception e) {
                e.printStackTrace();

                queryCharacterDetailsDb(characterId, callback);
            }
        });
    }

    private void queryCharacterDetailsDb(int characterId, DataCallback<CharacterModel> callback) {
        ioTaskExecutor.execute(() -> {
            try {
                CharacterModel character = datastore.queryCharacter(characterId);

                mainTaskExecutor.execute(() -> {
                    if (character != null) {
                        if (callback != null) callback.onSuccess(character);
                    } else {
                        if (callback != null) callback.onError(new NoOfflineDataException());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                mainTaskExecutor.execute(() -> {
                    if (callback != null) callback.onError(e);
                });
            }
        });
    }

    @Override
    public void killCharacter(int characterId, DataCallback<CharacterModel> callback) {
        ioTaskExecutor.execute(() -> {
            try {
                datastore.killCharacter(characterId);
                CharacterModel character = datastore.queryCharacter(characterId);

                mainTaskExecutor.execute(() -> {
                    if (character != null) {
                        if (callback != null) callback.onSuccess(character);
                    } else {
                        if (callback != null) callback.onError(new NoOfflineDataException());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                mainTaskExecutor.execute(() -> {
                    if (callback != null) callback.onError(e);
                });
            }
        });
    }
}
