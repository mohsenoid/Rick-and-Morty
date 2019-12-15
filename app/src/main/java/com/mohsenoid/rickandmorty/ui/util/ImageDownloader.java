package com.mohsenoid.rickandmorty.ui.util;

import android.widget.ImageView;
import android.widget.ProgressBar;

public interface ImageDownloader {

    void downloadImage(String imageUrl, ImageView imageView, ProgressBar progress);

    String extractFileName(String url);
}
