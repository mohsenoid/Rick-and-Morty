package com.mohsenoid.rickandmorty.data;

import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.model.EpisodeModel;

import java.util.List;

public interface Repository {

    void queryEpisodes(int page, DataCallback<List<EpisodeModel>> callback);

    void queryCharacters(int page, DataCallback<List<CharacterModel>> callback);

    void queryCharacterDetails(int characterId, DataCallback<CharacterModel> callback);
}
