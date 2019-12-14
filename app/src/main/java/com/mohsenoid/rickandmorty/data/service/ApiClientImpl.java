package com.mohsenoid.rickandmorty.data.service;

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

    private NetworkHelper networkHelper;

    ApiClientImpl(NetworkHelper networkHelper) {
        this.networkHelper = networkHelper;
    }

    @Override
    public List<EpisodeModel> getEpisodes(int page) throws IOException {
        ArrayList<NetworkHelper.Param> params = new ArrayList<>();
        params.add(new NetworkHelper.Param(ApiConstants.PARAM_KEY_PAGE, page + ""));

        try {
            String jsonResponse = networkHelper.requestData(ApiConstants.EPISODE_ENDPOINT, params);
            EpisodesResponse episodesResponse = EpisodesResponse.fromJson(jsonResponse);

            return episodesResponse.getResults();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<CharacterModel> getCharacters(int page) throws IOException {
        ArrayList<NetworkHelper.Param> params = new ArrayList<>();
        params.add(new NetworkHelper.Param(ApiConstants.PARAM_KEY_PAGE, page + ""));

        try {
            String jsonResponse = networkHelper.requestData(ApiConstants.CHARACTER_ENDPOINT, params);
            CharactersResponse charactersResponse = CharactersResponse.fromJson(jsonResponse);

            return charactersResponse.getResults();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public CharacterModel getCharacterDetails(int characterId) throws IOException {
        String characterEndpoint = ApiConstants.CHARACTER_ENDPOINT + characterId;

        try {
            String jsonResponse = networkHelper.requestData(characterEndpoint, null);
            CharacterModel character = CharacterModel.fromJson(jsonResponse);

            return character;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
