package com.mohsenoid.rickandmorty.domain.characters.usecase

import com.mohsenoid.rickandmorty.domain.characters.CharacterRepository

class UpdateCharacterStatusUseCase(private val characterRepository: CharacterRepository) {
    suspend operator fun invoke(
        characterId: Int,
        isKilled: Boolean,
    ): Result {
        return if (characterRepository.updateCharacterStatus(characterId, isKilled)) {
            Result.Success
        } else {
            Result.Failure
        }
    }

    sealed interface Result {
        data object Success : Result

        data object Failure : Result
    }
}
