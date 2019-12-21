package com.mohsenoid.rickandmorty.data.network.dto;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class NetworkEpisodesResponse {

    private static final String TAG_INFO = "info";
    private static final String TAG_RESULTS = "results";

    private NetworkInfoModel info;
    private List<NetworkEpisodeModel> results;

    private NetworkEpisodesResponse(NetworkInfoModel info, List<NetworkEpisodeModel> results) {
        this.info = info;
        this.results = results;
    }

    public static NetworkEpisodesResponse fromJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);

        JSONObject infoJsonObject = jsonObject.getJSONObject(TAG_INFO);
        JSONArray resultsJsonArray = jsonObject.getJSONArray(TAG_RESULTS);

        NetworkInfoModel info = NetworkInfoModel.fromJson(infoJsonObject);
        List<NetworkEpisodeModel> results = NetworkEpisodeModel.fromJson(resultsJsonArray);

        return new NetworkEpisodesResponse(info, results);
    }

    public NetworkInfoModel getInfo() {
        return info;
    }

    public void setInfo(NetworkInfoModel info) {
        this.info = info;
    }

    public List<NetworkEpisodeModel> getResults() {
        return results;
    }

    public void setResults(List<NetworkEpisodeModel> results) {
        this.results = results;
    }
}