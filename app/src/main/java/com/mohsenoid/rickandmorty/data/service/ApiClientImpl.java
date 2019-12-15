package com.mohsenoid.rickandmorty.data.service;

import androidx.annotation.VisibleForTesting;

import com.mohsenoid.rickandmorty.data.Serializer;
import com.mohsenoid.rickandmorty.data.service.model.CharactersResponse;
import com.mohsenoid.rickandmorty.data.service.model.EpisodesResponse;
import com.mohsenoid.rickandmorty.data.service.network.NetworkHelper;
import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.model.EpisodeModel;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApiClientImpl implements ApiClient {

    @VisibleForTesting
    public static ApiClientImpl instance;

    private NetworkHelper networkHelper;

    ApiClientImpl(NetworkHelper networkHelper) {
        this.networkHelper = networkHelper;
    }

    public static ApiClientImpl getInstance(NetworkHelper networkHelper) {
        if (instance == null)
            instance = new ApiClientImpl(networkHelper);

        return instance;
    }

    @Override
    public List<EpisodeModel> getEpisodes(int page) throws IOException, JSONException {
        ArrayList<NetworkHelper.Param> params = new ArrayList<>();
        params.add(new NetworkHelper.Param(ApiConstants.PARAM_KEY_PAGE, page + ""));

        String jsonResponse = networkHelper.requestData(ApiConstants.EPISODE_ENDPOINT, params);
        EpisodesResponse episodesResponse = EpisodesResponse.fromJson(jsonResponse);

        return episodesResponse.getResults();
    }

    @Override
    public List<CharacterModel> getCharacters(List<Integer> characterIds) throws IOException, JSONException {
        if (characterIds == null || characterIds.size() == 0) return null;

        String characterEndpoint = ApiConstants.CHARACTER_ENDPOINT + "[" + Serializer.serializeIntegerList(characterIds) + "]";

        String jsonResponse = networkHelper.requestData(characterEndpoint, null);
        CharactersResponse charactersResponse = CharactersResponse.fromJson(jsonResponse);

        return charactersResponse.getResults();
    }

    @Override
    public CharacterModel getCharacterDetails(int characterId) throws IOException, JSONException {
        String characterEndpoint = ApiConstants.CHARACTER_ENDPOINT + characterId;

        String jsonResponse = networkHelper.requestData(characterEndpoint, null);

        return CharacterModel.fromJson(jsonResponse);
    }
}
