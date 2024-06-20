package com.mohsenoid.rickandmorty.data.api.model

import kotlinx.serialization.SerialName

data class ApiErrorResponse(

    @SerialName(value = "error")
    val error: String? = null,

    @SerialName(value = "message")
    val message: String? = null,

    @SerialName(value = "orgMessage")
    val orgMessage: String? = null,

    @SerialName(value = "httpStatus")
    val httpStatus: Int? = null,
)
