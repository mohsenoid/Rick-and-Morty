package com.mohsenoid.rickandmorty.data.service;

import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.model.EpisodeModel;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface ApiClient {

    List<EpisodeModel> getEpisodes(int page) throws IOException, JSONException;

    List<CharacterModel> getCharacters(int page) throws IOException, JSONException;

    CharacterModel getCharacterDetails(int characterId) throws IOException, JSONException;
}
