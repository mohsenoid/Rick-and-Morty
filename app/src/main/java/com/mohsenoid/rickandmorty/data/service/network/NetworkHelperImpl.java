package com.mohsenoid.rickandmorty.data.service.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NetworkHelperImpl implements NetworkHelper {

    private String baseUrl;

    NetworkHelperImpl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String requestData(String endpoint, List<Param> params) throws IOException {
        StringBuilder response = new StringBuilder();

        URL url;
        HttpURLConnection urlConnection = null;
        try {
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append(baseUrl);
            urlBuilder.append(endpoint);
            for (Param param : params) {
                urlBuilder.append("?" + param.getKey() + "=" + param.getValue());
            }

            url = new URL(urlBuilder.toString());

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = urlConnection.getInputStream();

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);


            int data = inputStreamReader.read();
            while (data != -1) {
                response.append((char) data);
                data = inputStreamReader.read();
            }
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return response.toString();
    }

}
