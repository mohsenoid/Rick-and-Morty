package com.mohsenoid.rickandmorty.domain

class NoInternetConnectionException(message: String?) : Exception(message)

class EndOfListException : Exception("End of list")
