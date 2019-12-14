package com.mohsenoid.rickandmorty.model;

import org.json.JSONException;
import org.json.JSONObject;

public class LocationModel {

    private static final String TAG_NAME = "name";
    private static final String TAG_URL = "url";

    private String name;
    private String url;

    public LocationModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    static LocationModel fromJson(JSONObject jsonObject) throws JSONException {
        String name = jsonObject.getString(TAG_NAME);
        String url = jsonObject.getString(TAG_URL);

        LocationModel location = new LocationModel(name, url);
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationModel that = (LocationModel) o;
        return name.equals(that.name) &&
                url.equals(that.url);
    }
}
