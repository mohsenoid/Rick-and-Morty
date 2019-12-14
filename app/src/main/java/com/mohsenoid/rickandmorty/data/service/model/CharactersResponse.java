package com.mohsenoid.rickandmorty.data.service.model;

import com.mohsenoid.rickandmorty.model.CharacterModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CharactersResponse {
    private InfoModel info;
    private List<CharacterModel> results;

    private CharactersResponse(InfoModel info, List<CharacterModel> results) {
        this.info = info;
        this.results = results;
    }

    public static CharactersResponse fromJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);

        JSONObject infoJsonObject = jsonObject.getJSONObject("info");
        JSONArray resultsJsonArray = jsonObject.getJSONArray("results");

        InfoModel info = InfoModel.fromJson(infoJsonObject);
        List<CharacterModel> results = CharacterModel.fromJson(resultsJsonArray);

        CharactersResponse response = new CharactersResponse(info, results);
        return response;
    }

    public InfoModel getInfo() {
        return info;
    }

    public void setInfo(InfoModel info) {
        this.info = info;
    }

    public List<CharacterModel> getResults() {
        return results;
    }

    public void setResults(List<CharacterModel> results) {
        this.results = results;
    }
}