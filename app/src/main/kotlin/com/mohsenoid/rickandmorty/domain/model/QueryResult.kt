package com.mohsenoid.rickandmorty.domain.model

sealed interface QueryResult<out T> {
    data class Successful<out T>(val data: T) : QueryResult<T>
    object NoCache : QueryResult<Nothing>
}
