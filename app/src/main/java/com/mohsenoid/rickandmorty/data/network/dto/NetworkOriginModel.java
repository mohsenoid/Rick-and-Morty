package com.mohsenoid.rickandmorty.data.network.dto;

import androidx.annotation.VisibleForTesting;

import org.json.JSONException;
import org.json.JSONObject;

public class NetworkOriginModel {

    private static final String TAG_NAME = "name";
    private static final String TAG_URL = "url";

    private String name;
    private String url;

    @VisibleForTesting
    public NetworkOriginModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    static NetworkOriginModel fromJson(JSONObject jsonObject) throws JSONException {
        String name = jsonObject.getString(TAG_NAME);
        String url = jsonObject.getString(TAG_URL);

        return new NetworkOriginModel(name, url);
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NetworkOriginModel that = (NetworkOriginModel) o;
        return name.equals(that.name) &&
                url.equals(that.url);
    }
}
