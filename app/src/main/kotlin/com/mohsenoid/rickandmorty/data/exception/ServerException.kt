package com.mohsenoid.rickandmorty.data.exception

class ServerException(code: Int, error: String?) : Exception("API call error code: $code\n$error")
