package com.mohsenoid.rickandmorty.data.network;

import androidx.annotation.VisibleForTesting;

import com.mohsenoid.rickandmorty.data.Serializer;
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel;
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharactersResponse;
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel;
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodesResponse;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NetworkClientImpl implements NetworkClient {

    @VisibleForTesting
    public static NetworkClientImpl instance;

    private final NetworkHelper networkHelper;

    private NetworkClientImpl(NetworkHelper networkHelper) {
        this.networkHelper = networkHelper;
    }

    public static synchronized NetworkClientImpl getInstance(NetworkHelper networkHelper) {
        if (instance == null)
            instance = new NetworkClientImpl(networkHelper);

        return instance;
    }

    @Override
    public List<NetworkEpisodeModel> getEpisodes(int page) throws IOException, JSONException {
        ArrayList<NetworkHelper.Param> params = new ArrayList<>();
        params.add(new NetworkHelper.Param(NetworkConstants.PARAM_KEY_PAGE, page + ""));

        String jsonResponse = networkHelper.requestData(NetworkConstants.EPISODE_ENDPOINT, params);
        NetworkEpisodesResponse episodesResponse = NetworkEpisodesResponse.fromJson(jsonResponse);

        return episodesResponse.getResults();
    }

    @Override
    public List<NetworkCharacterModel> getCharactersByIds(List<Integer> characterIds) throws IOException, JSONException {
        if (characterIds == null || characterIds.size() == 0) return null;

        String characterEndpoint = NetworkConstants.CHARACTER_ENDPOINT + "[" + Serializer.serializeIntegerList(characterIds) + "]";

        String jsonResponse = networkHelper.requestData(characterEndpoint, null);
        NetworkCharactersResponse charactersResponse = NetworkCharactersResponse.fromJson(jsonResponse);

        return charactersResponse.getResults();
    }

    @Override
    public NetworkCharacterModel getCharacterDetails(int characterId) throws IOException, JSONException {
        String characterEndpoint = NetworkConstants.CHARACTER_ENDPOINT + characterId;

        String jsonResponse = networkHelper.requestData(characterEndpoint, null);

        return NetworkCharacterModel.fromJson(jsonResponse);
    }
}
