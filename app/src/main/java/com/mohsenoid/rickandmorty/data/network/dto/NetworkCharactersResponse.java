package com.mohsenoid.rickandmorty.data.network.dto;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class NetworkCharactersResponse {
    private List<NetworkCharacterModel> results;

    private NetworkCharactersResponse(List<NetworkCharacterModel> results) {
        this.results = results;
    }

    public static NetworkCharactersResponse fromJson(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);

        List<NetworkCharacterModel> results = NetworkCharacterModel.fromJson(jsonArray);

        return new NetworkCharactersResponse(results);
    }

    public List<NetworkCharacterModel> getResults() {
        return results;
    }

    public void setResults(List<NetworkCharacterModel> results) {
        this.results = results;
    }
}