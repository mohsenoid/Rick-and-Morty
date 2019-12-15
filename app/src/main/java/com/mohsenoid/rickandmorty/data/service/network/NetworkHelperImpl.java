package com.mohsenoid.rickandmorty.data.service.network;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class NetworkHelperImpl implements NetworkHelper {

    private static final int BUFFER_SIZE = 8192;

    private static NetworkHelperImpl instance;

    private String baseUrl;

    private NetworkHelperImpl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public static synchronized NetworkHelperImpl getInstance(String baseUrl) {
        if (instance == null)
            instance = new NetworkHelperImpl(baseUrl);

        return instance;
    }

    @Override
    public String requestData(String endpoint, List<Param> params) throws IOException {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(baseUrl);
        urlBuilder.append(endpoint);

        if (params != null) {
            for (Param param : params) {
                urlBuilder.append("?").append(param.getKey()).append("=").append(param.getValue());
            }
        }

        URL url = new URL(urlBuilder.toString());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        request(url, byteArrayOutputStream);

        return new String(byteArrayOutputStream.toByteArray());
    }

    @Override
    public void requestImageData(String imageUrl, File imageFile) throws IOException {
        URL url = new URL(imageUrl);
        FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
        request(url, fileOutputStream);
    }

    private void request(URL url, OutputStream output) throws IOException {
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();

        InputStream input = new BufferedInputStream(url.openStream(), BUFFER_SIZE);

        byte[] data = new byte[BUFFER_SIZE];

        int count;
        while ((count = input.read(data)) != -1) {
            output.write(data, 0, count);
        }

        output.flush();
        output.close();
        input.close();
    }
}
