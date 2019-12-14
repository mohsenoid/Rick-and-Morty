package com.mohsenoid.rickandmorty.data.service;

import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.model.EpisodeModel;

import java.io.IOException;
import java.util.List;

public interface ApiClient {

    List<EpisodeModel> getEpisodes(int page) throws IOException;

    List<CharacterModel> getCharacters(int page) throws IOException;

    CharacterModel getCharacterDetails(int characterId) throws IOException;
}
