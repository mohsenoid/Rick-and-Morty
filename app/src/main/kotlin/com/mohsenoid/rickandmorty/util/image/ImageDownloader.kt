package com.mohsenoid.rickandmorty.util.image

import android.widget.ImageView
import android.widget.ProgressBar

interface ImageDownloader {

    fun downloadImage(
        imageUrl: String,
        imageView: ImageView,
        progress: ProgressBar?
    )

    fun extractFileName(url: String): String
}
