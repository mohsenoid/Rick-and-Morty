package com.mohsenoid.rickandmorty.domain.characters.usecase

import com.mohsenoid.rickandmorty.domain.RepositoryGetResult
import com.mohsenoid.rickandmorty.domain.characters.CharacterRepository
import com.mohsenoid.rickandmorty.domain.characters.model.Character

class GetCharacterDetailsUseCase(private val characterRepository: CharacterRepository) {
    suspend operator fun invoke(characterId: Int): Result {
        return when (val result = characterRepository.getCharacter(characterId)) {
            is RepositoryGetResult.Success -> Result.Success(result.data)
            is RepositoryGetResult.Failure.NoConnection -> Result.NoConnection
            is RepositoryGetResult.Failure -> Result.Failure(result.message)
        }
    }

    sealed interface Result {
        data class Success(val character: Character) : Result

        data object NoConnection : Result

        data class Failure(val message: String) : Result
    }
}
