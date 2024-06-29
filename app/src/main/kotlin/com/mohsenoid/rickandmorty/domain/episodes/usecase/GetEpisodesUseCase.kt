package com.mohsenoid.rickandmorty.domain.episodes.usecase

import com.mohsenoid.rickandmorty.domain.RepositoryGetResult
import com.mohsenoid.rickandmorty.domain.episodes.EpisodeRepository
import com.mohsenoid.rickandmorty.domain.episodes.model.Episode

class GetEpisodesUseCase(private val episodeRepository: EpisodeRepository) {
    suspend operator fun invoke(page: Int = 0): Result {
        return when (val result = episodeRepository.getEpisodes(page)) {
            is RepositoryGetResult.Success -> Result.Success(result.data)
            is RepositoryGetResult.Failure.EndOfList -> Result.EndOfList
            is RepositoryGetResult.Failure.NoConnection -> Result.NoConnection
            is RepositoryGetResult.Failure.Unknown -> Result.Failure(result.message)
        }
    }

    sealed interface Result {
        data class Success(val episodes: List<Episode>) : Result

        data object EndOfList : Result

        data object NoConnection : Result

        data class Failure(val message: String) : Result
    }
}
