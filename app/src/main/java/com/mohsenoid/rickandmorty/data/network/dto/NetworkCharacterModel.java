package com.mohsenoid.rickandmorty.data.network.dto;

import androidx.annotation.VisibleForTesting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NetworkCharacterModel {

    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_STATUS = "status";
    private static final String TAG_SPECIES = "species";
    private static final String TAG_TYPE = "type";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_ORIGIN = "origin";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_IMAGE = "image";
    private static final String TAG_EPISODE = "episode";
    private static final String TAG_URL = "url";
    private static final String TAG_CREATED = "created";

    private Integer id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private NetworkOriginModel origin;
    private NetworkLocationModel location;
    private String image;
    private List<String> episodes;
    private String url;
    private String created;

    @VisibleForTesting
    public NetworkCharacterModel(Integer id, String name, String status, String species, String type, String gender, NetworkOriginModel origin, NetworkLocationModel location, String image, List<String> episodes, String url, String created) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.origin = origin;
        this.location = location;
        this.image = image;
        this.episodes = episodes;
        this.url = url;
        this.created = created;
    }

    static List<NetworkCharacterModel> fromJson(JSONArray jsonArray) throws JSONException {
        List<NetworkCharacterModel> characters = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject characterJsonObject = jsonArray.getJSONObject(i);
            NetworkCharacterModel character = NetworkCharacterModel.fromJson(characterJsonObject);
            characters.add(character);
        }

        return characters;
    }

    public static NetworkCharacterModel fromJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        return fromJson(jsonObject);
    }

    private static NetworkCharacterModel fromJson(JSONObject jsonObject) throws JSONException {
        int id = jsonObject.getInt(TAG_ID);
        String name = jsonObject.getString(TAG_NAME);
        String status = jsonObject.getString(TAG_STATUS);
        String species = jsonObject.getString(TAG_SPECIES);
        String type = jsonObject.getString(TAG_TYPE);
        String gender = jsonObject.getString(TAG_GENDER);
        NetworkOriginModel origin = NetworkOriginModel.fromJson(jsonObject.getJSONObject(TAG_ORIGIN));
        NetworkLocationModel location = NetworkLocationModel.fromJson(jsonObject.getJSONObject(TAG_LOCATION));
        String image = jsonObject.getString(TAG_IMAGE);
        String url = jsonObject.getString(TAG_URL);
        String created = jsonObject.getString(TAG_CREATED);

        JSONArray episodesJsonArray = jsonObject.getJSONArray(TAG_EPISODE);
        List<String> episodes = new ArrayList<>();
        for (int i = 0; i < episodesJsonArray.length(); i++) {
            String episode = episodesJsonArray.getString(i);
            episodes.add(episode);
        }

        return new NetworkCharacterModel(id, name, status, species, type, gender, origin, location, image, episodes, url, created);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSpecies() {
        return species;
    }

    public String getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }

    public NetworkOriginModel getOrigin() {
        return origin;
    }

    public NetworkLocationModel getLocation() {
        return location;
    }

    public String getImage() {
        return image;
    }

    public List<String> getEpisodes() {
        return episodes;
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
        NetworkCharacterModel that = (NetworkCharacterModel) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                status.equals(that.status) &&
                species.equals(that.species) &&
                type.equals(that.type) &&
                gender.equals(that.gender) &&
                origin.equals(that.origin) &&
                location.equals(that.location) &&
                image.equals(that.image) &&
                episodes.equals(that.episodes) &&
                url.equals(that.url) &&
                created.equals(that.created);
    }
}
