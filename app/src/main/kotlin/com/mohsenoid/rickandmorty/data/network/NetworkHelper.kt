package com.mohsenoid.rickandmorty.data.network

import java.io.File

interface NetworkHelper {

    fun requestData(endpoint: String, params: List<Param>?): String

    fun requestImageData(imageUrl: String, imageFile: File)

    data class Param(val key: String, val value: String)
}
