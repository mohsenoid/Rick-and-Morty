package com.mohsenoid.rickandmorty.domain;

import com.mohsenoid.rickandmorty.data.DataCallback;
import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.model.EpisodeModel;

import java.util.List;

public interface Repository {

    void queryEpisodes(int page, DataCallback<List<EpisodeModel>> callback);

    void queryCharacters(List<Integer> characterIds, DataCallback<List<CharacterModel>> callback);

    void queryCharacterDetails(int characterId, DataCallback<CharacterModel> callback);

    void killCharacter(int characterId, DataCallback<CharacterModel> listDataCallback);
}
