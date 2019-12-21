package com.mohsenoid.rickandmorty.data;

import androidx.annotation.VisibleForTesting;

import com.mohsenoid.rickandmorty.data.db.Db;
import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel;
import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel;
import com.mohsenoid.rickandmorty.data.exception.EndOfListException;
import com.mohsenoid.rickandmorty.data.exception.NoOfflineDataException;
import com.mohsenoid.rickandmorty.data.mapper.CharacterMapper;
import com.mohsenoid.rickandmorty.data.mapper.EpisodeMapper;
import com.mohsenoid.rickandmorty.data.network.NetworkClient;
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel;
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel;
import com.mohsenoid.rickandmorty.domain.Repository;
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity;
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity;
import com.mohsenoid.rickandmorty.util.config.ConfigProvider;
import com.mohsenoid.rickandmorty.util.executor.TaskExecutor;

import java.util.List;

public class RepositoryImpl implements Repository {

    @VisibleForTesting
    public static RepositoryImpl instance;

    private final Db db;
    private final NetworkClient networkClient;
    private final TaskExecutor ioTaskExecutor;
    private final TaskExecutor mainTaskExecutor;
    private final ConfigProvider configProvider;
    private final EpisodeMapper episodeMapper;
    private final CharacterMapper characterMapper;

    private RepositoryImpl(Db db, NetworkClient networkClient, TaskExecutor ioTaskExecutor, TaskExecutor mainTaskExecutor, ConfigProvider configProvider, EpisodeMapper episodeMapper, CharacterMapper characterMapper) {
        this.db = db;
        this.networkClient = networkClient;
        this.ioTaskExecutor = ioTaskExecutor;
        this.mainTaskExecutor = mainTaskExecutor;
        this.configProvider = configProvider;
        this.episodeMapper = episodeMapper;
        this.characterMapper = characterMapper;
    }

    public static synchronized RepositoryImpl getInstance(Db db, NetworkClient networkClient, TaskExecutor ioTaskExecutor, TaskExecutor mainTaskExecutor, ConfigProvider configProvider, EpisodeMapper episodeMapper, CharacterMapper characterMapper) {
        if (instance == null)
            instance = new RepositoryImpl(db, networkClient, ioTaskExecutor, mainTaskExecutor, configProvider, episodeMapper, characterMapper);

        return instance;
    }

    @Override
    public void queryEpisodes(int page, DataCallback<List<EpisodeEntity>> callback) {
        if (configProvider.isOnline()) {
            queryNetworkEpisodes(page, callback);
        } else {
            queryDbEpisodes(page, callback);
        }
    }

    private void queryNetworkEpisodes(int page, DataCallback<List<EpisodeEntity>> callback) {
        ioTaskExecutor.execute(() -> {
            try {
                List<NetworkEpisodeModel> networkEpisodes = networkClient.getEpisodes(page);


                for (NetworkEpisodeModel networkEpisode : networkEpisodes) {
                    DbEpisodeModel dbEpisode = episodeMapper.dbMapper.map(networkEpisode);
                    db.insertEpisode(dbEpisode);
                }

                queryDbEpisodes(page, callback);
            } catch (Exception e) {
                e.printStackTrace();

                queryDbEpisodes(page, callback);
            }
        });
    }

    private void queryDbEpisodes(int page, DataCallback<List<EpisodeEntity>> callback) {
        ioTaskExecutor.execute(() -> {
            try {
                List<DbEpisodeModel> dbEpisodes = db.queryAllEpisodes(page);
                List<EpisodeEntity> episodes = episodeMapper.entityMapper.map(dbEpisodes);

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
    public void queryCharactersByIds(List<Integer> characterIds, DataCallback<List<CharacterEntity>> callback) {
        if (configProvider.isOnline()) {
            queryNetworkCharactersByIds(characterIds, callback);
        } else {
            queryDbCharactersByIds(characterIds, callback);
        }
    }

    private void queryNetworkCharactersByIds(List<Integer> characterIds, DataCallback<List<CharacterEntity>> callback) {
        ioTaskExecutor.execute(() -> {
            try {
                List<NetworkCharacterModel> networkCharacters = networkClient.getCharactersByIds(characterIds);

                for (NetworkCharacterModel networkCharacter : networkCharacters) {
                    DbCharacterModel dbCharacter = characterMapper.dbMapper.map(networkCharacter);
                    db.insertCharacter(dbCharacter);
                }

                queryDbCharactersByIds(characterIds, callback);
            } catch (Exception e) {
                e.printStackTrace();

                queryDbCharactersByIds(characterIds, callback);
            }
        });
    }

    private void queryDbCharactersByIds(List<Integer> characterIds, DataCallback<List<CharacterEntity>> callback) {
        ioTaskExecutor.execute(() -> {
            try {
                List<DbCharacterModel> dbCharacters = db.queryCharactersByIds(characterIds);
                List<CharacterEntity> characters = characterMapper.entityMapper.map(dbCharacters);

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
    public void queryCharacterDetails(int characterId, DataCallback<CharacterEntity> callback) {
        if (configProvider.isOnline()) {
            queryNetworkCharacterDetails(characterId, callback);
        } else {
            queryDbCharacterDetails(characterId, callback);
        }
    }

    private void queryNetworkCharacterDetails(int characterId, DataCallback<CharacterEntity> callback) {
        ioTaskExecutor.execute(() -> {
            try {
                NetworkCharacterModel networkCharacter = networkClient.getCharacterDetails(characterId);
                DbCharacterModel dbCharacterModel = characterMapper.dbMapper.map(networkCharacter);

                db.insertCharacter(dbCharacterModel);

                queryDbCharacterDetails(characterId, callback);
            } catch (Exception e) {
                e.printStackTrace();

                queryDbCharacterDetails(characterId, callback);
            }
        });
    }

    private void queryDbCharacterDetails(int characterId, DataCallback<CharacterEntity> callback) {
        ioTaskExecutor.execute(() -> {
            try {
                DbCharacterModel dbCharacter = db.queryCharacter(characterId);
                CharacterEntity character = characterMapper.entityMapper.map(dbCharacter);

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
    public void killCharacter(int characterId, DataCallback<CharacterEntity> callback) {
        ioTaskExecutor.execute(() -> {
            try {
                db.killCharacter(characterId);
                queryCharacterDetails(characterId, callback);
            } catch (Exception e) {
                e.printStackTrace();
                mainTaskExecutor.execute(() -> {
                    if (callback != null) callback.onError(e);
                });
            }
        });
    }
}
