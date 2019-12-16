package com.mohsenoid.rickandmorty.ui.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import com.mohsenoid.rickandmorty.data.service.network.NetworkHelper;
import com.mohsenoid.rickandmorty.executor.TaskExecutor;

import java.io.File;
import java.io.IOException;

public class ImageDownloaderImpl implements ImageDownloader {

    private static final String SEPARATOR = "/";

    @VisibleForTesting
    public static ImageDownloaderImpl instance;

    private final NetworkHelper networkHelper;
    private final String cacheDirectoryPath;
    private final TaskExecutor ioTaskExecutor;
    private final TaskExecutor mainTaskExecutor;

    private ImageDownloaderImpl(NetworkHelper networkHelper, String cacheDirectoryPath, TaskExecutor ioTaskExecutor, TaskExecutor mainTaskExecutor) {
        this.networkHelper = networkHelper;
        this.cacheDirectoryPath = cacheDirectoryPath;
        this.ioTaskExecutor = ioTaskExecutor;
        this.mainTaskExecutor = mainTaskExecutor;
    }

    public static synchronized ImageDownloaderImpl getInstance(NetworkHelper networkHelper, String cacheDirectoryPath, TaskExecutor ioTaskExecutor, TaskExecutor mainTaskExecutor) {
        if (instance == null)
            instance = new ImageDownloaderImpl(networkHelper, cacheDirectoryPath, ioTaskExecutor, mainTaskExecutor);

        return instance;
    }

    @Override
    public void downloadImage(String imageUrl, ImageView imageView, @Nullable ProgressBar progress) {
        imageView.setVisibility(View.INVISIBLE);
        if (progress != null) progress.setVisibility(View.VISIBLE);

        ioTaskExecutor.execute(() -> {
            String fileName = extractFileName(imageUrl);
            File imageFile = new File(cacheDirectoryPath + File.separator + fileName);


            if (!imageFile.exists()) {
                try {
                    networkHelper.requestImageData(imageUrl, imageFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Bitmap bitmap = loadBitmapFile(imageFile);

            mainTaskExecutor.execute(() -> {
                if (progress != null) progress.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);
            });
        });
    }

    @Override
    public String extractFileName(String url) {
        if (url == null) return null;

        String[] urlParts = url.split(SEPARATOR);
        return urlParts[urlParts.length - 1];
    }

    private Bitmap loadBitmapFile(File imageFile) {
        try {
            return BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
