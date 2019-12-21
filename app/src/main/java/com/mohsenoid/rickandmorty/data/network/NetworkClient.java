package com.mohsenoid.rickandmorty.data.network;

import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel;
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface NetworkClient {

    List<NetworkEpisodeModel> getEpisodes(int page) throws IOException, JSONException;

    List<NetworkCharacterModel> getCharactersByIds(List<Integer> characterIds) throws IOException, JSONException;

    NetworkCharacterModel getCharacterDetails(int characterId) throws IOException, JSONException;
}
