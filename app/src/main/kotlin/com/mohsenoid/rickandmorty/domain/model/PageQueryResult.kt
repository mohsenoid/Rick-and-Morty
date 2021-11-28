package com.mohsenoid.rickandmorty.domain.model

sealed interface PageQueryResult<out T> {
    data class Successful<out T>(val data: T) : PageQueryResult<T>
    object EndOfList : PageQueryResult<Nothing>
    object Error : PageQueryResult<Nothing>
}
