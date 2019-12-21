package com.mohsenoid.rickandmorty.data.db;

import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel;
import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel;
import com.mohsenoid.rickandmorty.data.exception.NoOfflineDataException;

import java.util.List;

public interface Db {

    void insertEpisode(DbEpisodeModel episode);

    List<DbEpisodeModel> queryAllEpisodes(int page);

    void insertCharacter(DbCharacterModel character);

    List<DbCharacterModel> queryCharactersByIds(List<Integer> characterIds);

    DbCharacterModel queryCharacter(int characterId);

    void killCharacter(int characterId) throws NoOfflineDataException;
}
