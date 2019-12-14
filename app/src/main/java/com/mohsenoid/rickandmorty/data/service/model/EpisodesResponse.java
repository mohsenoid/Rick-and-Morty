package com.mohsenoid.rickandmorty.data.service.model;

import com.mohsenoid.rickandmorty.model.EpisodeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class EpisodesResponse {
    private InfoModel info;
    private List<EpisodeModel> results;

    private EpisodesResponse(InfoModel info, List<EpisodeModel> results) {
        this.info = info;
        this.results = results;
    }

    public static EpisodesResponse fromJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);

        JSONObject infoJsonObject = jsonObject.getJSONObject("info");
        JSONArray resultsJsonArray = jsonObject.getJSONArray("results");

        InfoModel info = InfoModel.fromJson(infoJsonObject);
        List<EpisodeModel> results = EpisodeModel.fromJson(resultsJsonArray);

        EpisodesResponse response = new EpisodesResponse(info, results);
        return response;
    }

    public InfoModel getInfo() {
        return info;
    }

    public void setInfo(InfoModel info) {
        this.info = info;
    }

    public List<EpisodeModel> getResults() {
        return results;
    }

    public void setResults(List<EpisodeModel> results) {
        this.results = results;
    }
}