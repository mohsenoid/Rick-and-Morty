package com.mohsenoid.rickandmorty.data.network.dto

import com.google.gson.annotations.SerializedName

data class NetworkInfoModel(

    @SerializedName(value = "count")
    val count: Int,

    @SerializedName(value = "pages")
    val pages: Int,

    @SerializedName(value = "next")
    val next: String,

    @SerializedName(value = "prev")
    val prev: String
)
