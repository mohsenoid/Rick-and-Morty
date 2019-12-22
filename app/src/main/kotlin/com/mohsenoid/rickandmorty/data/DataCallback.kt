package com.mohsenoid.rickandmorty.data

interface DataCallback<T> {

    fun onSuccess(result: T)

    fun onError(exception: Exception)
}
