package com.mohsenoid.rickandmorty

import com.mohsenoid.rickandmorty.data.dataModules
import com.mohsenoid.rickandmorty.domain.characters.CharacterRepository
import com.mohsenoid.rickandmorty.domain.characters.usecase.GetCharacterDetailsUseCase
import com.mohsenoid.rickandmorty.domain.characters.usecase.GetCharactersUseCase
import com.mohsenoid.rickandmorty.domain.characters.usecase.UpdateCharacterStatusUseCase
import com.mohsenoid.rickandmorty.domain.domainModules
import com.mohsenoid.rickandmorty.domain.episodes.EpisodeRepository
import com.mohsenoid.rickandmorty.domain.episodes.usecase.GetEpisodesUseCase
import com.mohsenoid.rickandmorty.ui.uiModule
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify
import org.koin.test.verify.verifyAll
import kotlin.test.Test

@OptIn(KoinExperimentalAPI::class)
class KoinModulesTest : KoinTest {
    @Test
    fun `Verify data modules`() {
        dataModules.verifyAll()
    }

    @Test
    fun `Verify domain modules`() {
        domainModules.verifyAll(
            extraTypes =
                listOf(
                    EpisodeRepository::class,
                    CharacterRepository::class,
                ),
        )
    }

    @Test
    fun `Verify ui modules`() {
        uiModule.verify(
            extraTypes =
                listOf(
                    Set::class,
                    GetEpisodesUseCase::class,
                    GetCharactersUseCase::class,
                    GetCharacterDetailsUseCase::class,
                    UpdateCharacterStatusUseCase::class,
                ),
        )
    }
}
