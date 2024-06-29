package com.mohsenoid.rickandmorty.domain.characters

import com.mohsenoid.rickandmorty.domain.characters.usecase.GetCharacterDetailsUseCase
import com.mohsenoid.rickandmorty.domain.characters.usecase.GetCharactersUseCase
import com.mohsenoid.rickandmorty.domain.characters.usecase.UpdateCharacterStatusUseCase
import org.koin.dsl.module

internal val characterModule =
    module {
        factory {
            GetCharactersUseCase(characterRepository = get())
        }

        factory {
            GetCharacterDetailsUseCase(characterRepository = get())
        }

        factory {
            UpdateCharacterStatusUseCase(characterRepository = get())
        }
    }
