package com.mohsenoid.rickandmorty.data.network

interface NetworkHelper {

    fun requestData(endpoint: String, params: List<Param>?): String

    data class Param(val key: String, val value: String)
}
