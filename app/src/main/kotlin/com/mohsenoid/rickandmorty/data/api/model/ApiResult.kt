package com.mohsenoid.rickandmorty.data.api.model

sealed class ApiResult<out R> {

    data class Success<R>(val data: R) : ApiResult<R>()

    sealed class Error : ApiResult<Nothing>() {

        data class ServerError(val reason: Reason) : Error() {

            enum class Reason {
                UNAUTHORIZED,
                BAD_REQUEST,
                FORBIDDEN,
                SERVICE_UNAVAILABLE,
                SERVER_UNREACHABLE,
                UNKNOWN
            }
        }

        data class UnknownError(val exception: Throwable) : Error()
    }
}
