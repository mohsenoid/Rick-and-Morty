package com.mohsenoid.rickandmorty.domain.characters.usecase

import com.mohsenoid.rickandmorty.domain.NoInternetConnectionException
import com.mohsenoid.rickandmorty.domain.characters.CharacterRepository
import com.mohsenoid.rickandmorty.domain.characters.model.Character

class GetCharactersUseCase(private val characterRepository: CharacterRepository) {
    suspend operator fun invoke(charactersIds: Set<Int>): Result {
        return characterRepository.getCharacters(charactersIds).fold(
            onSuccess = { characters ->
                Result.Success(characters)
            },
            onFailure = { exception ->
                when (exception) {
                    is NoInternetConnectionException -> Result.NoInternetConnection
                    else -> Result.Failure(exception.message ?: "Unknown Error")
                }
            },
        )
    }

    sealed interface Result {
        data class Success(val characters: List<Character>) : Result

        data object NoInternetConnection : Result

        data class Failure(val message: String) : Result
    }
}
