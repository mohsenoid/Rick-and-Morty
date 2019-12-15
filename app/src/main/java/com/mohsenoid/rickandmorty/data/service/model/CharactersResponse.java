package com.mohsenoid.rickandmorty.data.service.model;

import com.mohsenoid.rickandmorty.model.CharacterModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class CharactersResponse {
    private List<CharacterModel> results;

    private CharactersResponse(List<CharacterModel> results) {
        this.results = results;
    }

    public static CharactersResponse fromJson(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);

        List<CharacterModel> results = CharacterModel.fromJson(jsonArray);

        CharactersResponse response = new CharactersResponse(results);
        return response;
    }

    public List<CharacterModel> getResults() {
        return results;
    }

    public void setResults(List<CharacterModel> results) {
        this.results = results;
    }
}