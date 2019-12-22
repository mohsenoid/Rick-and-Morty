package com.mohsenoid.rickandmorty.util.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.mohsenoid.rickandmorty.data.network.NetworkHelper
import com.mohsenoid.rickandmorty.util.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.IOException

class ImageDownloaderImpl(
    private val networkHelper: NetworkHelper,
    private val cacheDirectoryPath: String,
    private val dispatcherProvider: DispatcherProvider
) : ImageDownloader {

    override suspend fun downloadImage(
        imageUrl: String,
        imageView: ImageView,
        progress: ProgressBar?
    ) {
        imageView.visibility = View.INVISIBLE
        progress?.isVisible = true

        withContext(dispatcherProvider.ioDispatcher) {
            val fileName = extractFileName(imageUrl)
            val imageFile = File(cacheDirectoryPath + File.separator + fileName)
            if (!imageFile.exists()) {
                try {
                    networkHelper.requestImageData(imageUrl, imageFile)
                } catch (e: IOException) {
                    e.printStackTrace()
                    if (imageFile.delete()) Timber.w("Image file ${imageFile.name} deleted!")
                }
            }
            val bitmap = loadBitmapFile(imageFile, imageView.width, imageView.height)
            withContext(dispatcherProvider.mainDispatcher) {
                progress?.isVisible = false
                imageView.isVisible = true
                imageView.setImageBitmap(bitmap)
            }
        }
    }

    override fun extractFileName(url: String): String {
        return url.split(SEPARATOR).last()
    }

    private fun loadBitmapFile(imageFile: File, outWidth: Int, outHeight: Int): Bitmap? {
        return try {
            val options = BitmapFactory.Options()
            options.outWidth = outWidth
            options.outHeight = outHeight
            BitmapFactory.decodeFile(imageFile.absolutePath, options)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        private const val SEPARATOR = "/"
    }
}
