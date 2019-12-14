package com.mohsenoid.rickandmorty.data.service;

import com.mohsenoid.rickandmorty.data.service.model.EpisodesResponse;
import com.mohsenoid.rickandmorty.data.service.network.NetworkHelper;
import com.mohsenoid.rickandmorty.model.EpisodeModel;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApiClientImpl implements ApiClient {

    private NetworkHelper helper;

    ApiClientImpl(NetworkHelper helper) {
        this.helper = helper;
    }

    @Override
    public List<EpisodeModel> getEpisodes(int page) throws IOException {
        ArrayList<NetworkHelper.Param> params = new ArrayList<>();
        params.add(new NetworkHelper.Param(ApiConstants.PARAM_KEY_PAGE, page + ""));

        try {
            String jsonResponse = helper.requestData(ApiConstants.EPISODE_ENDPOINT, params);
            EpisodesResponse episodesResponse = EpisodesResponse.fromJson(jsonResponse);

            return episodesResponse.getResults();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
