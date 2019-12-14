package com.mohsenoid.rickandmorty.model;

import com.mohsenoid.rickandmorty.utils.Serializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EpisodeModel implements Serializable {

    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_AIR_DATE = "air_date";
    private static final String TAG_EPISODE = "episode";
    private static final String TAG_URL = "url";
    private static final String TAG_CREATED = "created";
    private static final String TAG_CHARACTERS = "characters";

    private Integer id;
    private String name;
    private String airDate;
    private String episode;
    private List<String> characters;
    private String url;
    private String created;

    public EpisodeModel(Integer id, String name, String airDate, String episode, String characters, String url, String created) {
        this.id = id;
        this.name = name;
        this.airDate = airDate;
        this.episode = episode;
        this.characters = Serializer.deserializeStringList(characters);
        this.url = url;
        this.created = created;
    }

    public EpisodeModel(Integer id, String name, String airDate, String episode, List<String> characters, String url, String created) {
        this.id = id;
        this.name = name;
        this.airDate = airDate;
        this.episode = episode;
        this.characters = characters;
        this.url = url;
        this.created = created;
    }

    public static List<EpisodeModel> fromJson(JSONArray jsonArray) throws JSONException {
        List<EpisodeModel> episodes = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject episodeJsonObject = jsonArray.getJSONObject(i);
            EpisodeModel episode = EpisodeModel.fromJson(episodeJsonObject);
            episodes.add(episode);
        }

        return episodes;
    }

    public static EpisodeModel fromJson(JSONObject jsonObject) throws JSONException {
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

        EpisodeModel newEpisode = new EpisodeModel(id, name, airDate, episode, characters, url, created);
        return newEpisode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public void setCharacters(List<String> characters) {
        this.characters = characters;
    }

    public String getSerializedCharacters() {
        return Serializer.serializeStringList(this.characters);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EpisodeModel that = (EpisodeModel) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                airDate.equals(that.airDate) &&
                episode.equals(that.episode) &&
                getSerializedCharacters().equals(that.getSerializedCharacters()) &&
                url.equals(that.url) &&
                created.equals(that.created);
    }
}
