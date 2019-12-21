package com.mohsenoid.rickandmorty.data.network;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface NetworkHelper {

    String requestData(String endpoint, List<Param> params) throws IOException;

    void requestImageData(String imageUrl, File imageFile) throws IOException;

    class Param {
        private String key;
        private String value;

        Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

        String getKey() {
            return key;
        }

        String getValue() {
            return value;
        }
    }
}
