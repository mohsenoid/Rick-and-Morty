package com.mohsenoid.rickandmorty.data.db;

import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.model.EpisodeModel;

import java.util.List;

public interface Datastore {

    void insertEpisode(EpisodeModel episode);

    List<EpisodeModel> queryAllEpisodes(int page);

    void insertCharacter(CharacterModel character);

    List<CharacterModel> queryAllCharacters(int page);

    CharacterModel queryCharacter(int characterId);
}
