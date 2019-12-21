package com.mohsenoid.rickandmorty.data.network

import java.io.*
import java.net.URL

class NetworkHelperImpl(private val baseUrl: String) : NetworkHelper {

    override fun requestData(endpoint: String, params: List<NetworkHelper.Param>?): String {
        val urlBuilder = StringBuilder()
        urlBuilder.append(baseUrl)
        urlBuilder.append(endpoint)

        if (params != null) {
            for ((key, value) in params) {
                urlBuilder.append("?").append(key).append("=").append(value)
            }
        }

        val url = URL(urlBuilder.toString())
        val byteArrayOutputStream = ByteArrayOutputStream()
        request(url, byteArrayOutputStream)
        return String(byteArrayOutputStream.toByteArray())
    }

    override fun requestImageData(imageUrl: String, imageFile: File) {
        val url = URL(imageUrl)
        val fileOutputStream = FileOutputStream(imageFile)
        request(url, fileOutputStream)
    }

    private fun request(url: URL, output: OutputStream) {
        val urlConnection = url.openConnection()
        urlConnection.connect()
        BufferedInputStream(url.openStream(), BUFFER_SIZE).use { input ->
            val data = ByteArray(BUFFER_SIZE)
            var count: Int

            while (input.read(data).also { count = it } != -1) {
                output.write(data, 0, count)
            }
        }
        output.flush()
        output.close()
    }

    companion object {
        private const val BUFFER_SIZE = 8192
    }
}
