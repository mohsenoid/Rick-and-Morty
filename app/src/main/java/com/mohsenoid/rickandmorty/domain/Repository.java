package com.mohsenoid.rickandmorty.domain;

import com.mohsenoid.rickandmorty.data.DataCallback;
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity;
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity;

import java.util.List;

public interface Repository {

    void queryEpisodes(int page, DataCallback<List<EpisodeEntity>> callback);

    void queryCharactersByIds(List<Integer> characterIds, DataCallback<List<CharacterEntity>> callback);

    void queryCharacterDetails(int characterId, DataCallback<CharacterEntity> callback);

    void killCharacter(int characterId, DataCallback<CharacterEntity> listDataCallback);
}
