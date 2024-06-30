package com.mohsenoid.rickandmorty.domain.episodes.usecase

import com.mohsenoid.rickandmorty.domain.EndOfListException
import com.mohsenoid.rickandmorty.domain.NoInternetConnectionException
import com.mohsenoid.rickandmorty.domain.episodes.EpisodeRepository
import com.mohsenoid.rickandmorty.domain.episodes.model.Episode

class GetEpisodesUseCase(private val episodeRepository: EpisodeRepository) {
    suspend operator fun invoke(page: Int = 0): Result {
        return episodeRepository.getEpisodes(page).fold(
            onSuccess = { episodes -> Result.Success(episodes) },
            onFailure = { exception ->
                when (exception) {
                    is EndOfListException -> Result.EndOfList
                    is NoInternetConnectionException -> Result.NoInternetConnection
                    else -> Result.Failure(exception.message ?: "Unknown Error")
                }
            },
        )
    }

    sealed interface Result {
        data class Success(val episodes: List<Episode>) : Result

        data object EndOfList : Result

        data object NoInternetConnection : Result

        data class Failure(val message: String) : Result
    }
}
