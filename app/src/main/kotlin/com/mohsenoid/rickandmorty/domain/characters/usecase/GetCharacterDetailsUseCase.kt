package com.mohsenoid.rickandmorty.domain.characters.usecase

import com.mohsenoid.rickandmorty.domain.NoInternetConnectionException
import com.mohsenoid.rickandmorty.domain.characters.CharacterRepository
import com.mohsenoid.rickandmorty.domain.characters.model.Character

class GetCharacterDetailsUseCase(private val characterRepository: CharacterRepository) {
    suspend operator fun invoke(characterId: Int): Result {
        return characterRepository.getCharacter(characterId).fold(
            onSuccess = { character ->
                Result.Success(character)
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
        data class Success(val character: Character) : Result

        data object NoInternetConnection : Result

        data class Failure(val message: String) : Result
    }
}
