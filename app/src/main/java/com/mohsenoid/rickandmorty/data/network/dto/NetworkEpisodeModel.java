package com.mohsenoid.rickandmorty.data.network.dto;

import androidx.annotation.VisibleForTesting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NetworkEpisodeModel {

    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_AIR_DATE = "air_date";
    private static final String TAG_EPISODE = "episode";
    private static final String TAG_CHARACTERS = "characters";
    private static final String TAG_URL = "url";
    private static final String TAG_CREATED = "created";

    private Integer id;
    private String name;
    private String airDate;
    private String episode;
    private List<String> characters;
    private String url;
    private String created;

    @VisibleForTesting
    public NetworkEpisodeModel(Integer id, String name, String airDate, String episode, List<String> characters, String url, String created) {
        this.id = id;
        this.name = name;
        this.airDate = airDate;
        this.episode = episode;
        this.characters = characters;
        this.url = url;
        this.created = created;
    }

    static List<NetworkEpisodeModel> fromJson(JSONArray jsonArray) throws JSONException {
        List<NetworkEpisodeModel> episodes = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject episodeJsonObject = jsonArray.getJSONObject(i);
            NetworkEpisodeModel episode = NetworkEpisodeModel.fromJson(episodeJsonObject);
            episodes.add(episode);
        }

        return episodes;
    }

    private static NetworkEpisodeModel fromJson(JSONObject jsonObject) throws JSONException {
        int id = jsonObject.getInt(TAG_ID);
        String name = jsonObject.getString(TAG_NAME);
        String airDate = jsonObject.getString(TAG_AIR_DATE);
        String episode = jsonObject.getString(TAG_EPISODE);
        String url = jsonObject.getString(TAG_URL);
        String created = jsonObject.getString(TAG_CREATED);

        JSONArray charactersJsonArray = jsonObject.getJSONArray(TAG_CHARACTERS);
        List<String> characters = new ArrayList<>();
        for (int i = 0; i < charactersJsonArray.length(); i++) {
            String character = charactersJsonArray.getString(i);
            characters.add(character);
        }

        return new NetworkEpisodeModel(id, name, airDate, episode, characters, url, created);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAirDate() {
        return airDate;
    }

    public String getEpisode() {
        return episode;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public String getUrl() {
        return url;
    }

    public String getCreated() {
        return created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NetworkEpisodeModel that = (NetworkEpisodeModel) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                airDate.equals(that.airDate) &&
                episode.equals(that.episode) &&
                characters.equals(that.characters) &&
                url.equals(that.url) &&
                created.equals(that.created);
    }
}
