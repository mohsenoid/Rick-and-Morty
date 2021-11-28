package com.mohsenoid.rickandmorty.data.api.mapper

import com.mohsenoid.rickandmorty.data.api.model.ApiResult
import retrofit2.Response
import timber.log.Timber
import java.net.UnknownHostException

object ApiResultMapper {

    private const val HTTP_CODE_400_BAD_REQUEST = 400
    private const val HTTP_CODE_401_UNAUTHORIZED = 401
    private const val HTTP_CODE_403_FORBIDDEN = 403
    private const val HTTP_CODE_503_SERVICE_UNAVAILABLE = 503

    suspend inline fun <reified T : Any> toApiResult(
        crossinline apiCall: suspend () -> Response<T>,
    ): ApiResult<T> = kotlin.runCatching {
        val response = apiCall()
        if (response.isSuccessful) {
            handleSuccessfulApiResponse(response)
        } else {
            handleErrorApiResponse(response)
        }
    }.getOrElse { throwable ->
        Timber.e(throwable)

        when (throwable) {
            is UnknownHostException -> ApiResult.Error.ServerError(
                ApiResult.Error.ServerError.Reason.SERVER_UNREACHABLE
            )
            else -> ApiResult.Error.UnknownError(throwable)
        }
    }

    inline fun <reified T : Any> handleSuccessfulApiResponse(response: Response<T>): ApiResult<T> {
        val body = response.body()
        return when {
            body != null -> ApiResult.Success(body)
            else -> ApiResult.Error.UnknownError(
                exception = IllegalStateException("response body is null"),
            )
        }
    }

    fun handleErrorApiResponse(response: Response<*>): ApiResult.Error.ServerError {
        val reason = when (response.code()) {
            HTTP_CODE_400_BAD_REQUEST -> ApiResult.Error.ServerError.Reason.BAD_REQUEST
            HTTP_CODE_401_UNAUTHORIZED -> ApiResult.Error.ServerError.Reason.UNAUTHORIZED
            HTTP_CODE_403_FORBIDDEN -> ApiResult.Error.ServerError.Reason.FORBIDDEN
            HTTP_CODE_503_SERVICE_UNAVAILABLE -> ApiResult.Error.ServerError.Reason.SERVICE_UNAVAILABLE
            else -> ApiResult.Error.ServerError.Reason.UNKNOWN
        }
        return ApiResult.Error.ServerError(reason = reason)
    }
}
