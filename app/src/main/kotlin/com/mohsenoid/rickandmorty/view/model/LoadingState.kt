package com.mohsenoid.rickandmorty.view.model

sealed interface LoadingState {
    object None : LoadingState
    object Loading : LoadingState
    object LoadingMore : LoadingState
}
