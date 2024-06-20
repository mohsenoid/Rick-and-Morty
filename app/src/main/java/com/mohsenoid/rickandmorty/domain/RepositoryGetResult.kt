package com.mohsenoid.rickandmorty.domain

sealed interface RepositoryGetResult<T : Any> {
    data class Success<T : Any>(val data: T) : RepositoryGetResult<T>

    sealed interface Failure<T : Any> : RepositoryGetResult<T> {
        val message: String

        data class EndOfList<T : Any>(override val message: String) : Failure<T>

        data class NoConnection<T : Any>(override val message: String) : Failure<T>

        data class Unknown<T : Any>(override val message: String) : Failure<T>
    }

    fun getOrNull(): T? {
        return when (this) {
            is Success -> data
            is Failure -> null
        }
    }
}
