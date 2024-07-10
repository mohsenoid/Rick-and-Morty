package com.mohsenoid.rickandmorty.ui.characters.list

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mohsenoid.rickandmorty.domain.characters.model.Character
import com.mohsenoid.rickandmorty.ui.theme.RickAndMortyTheme

class CharactersScreenKtTest {
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun CharacterItemTest() {
        RickAndMortyTheme {
            CharacterItem(
                Character(
                    id = 1,
                    name = "Mohsen Sanchez",
                    isAlive = true,
                    isKilled = false,
                    species = "Human",
                    type = "",
                    gender = "Male",
                    origin = "Earth (C-137)",
                    location = "Citadel of Ricks",
                    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                ),
            )
        }
    }

    @Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun CharacterItemDeadTest() {
        RickAndMortyTheme {
            CharacterItem(
                Character(
                    id = 1,
                    name = "Rick Sanchez",
                    isAlive = false,
                    isKilled = false,
                    species = "Human",
                    type = "",
                    gender = "Male",
                    origin = "Earth (C-137)",
                    location = "Citadel of Ricks",
                    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                ),
            )
        }
    }

    @Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun CharactersListTest() {
        RickAndMortyTheme {
            CharactersList(
                characters =
                    listOf(
                        Character(
                            id = 1,
                            name = "Rick Sanchez",
                            isAlive = true,
                            isKilled = false,
                            species = "Human",
                            type = "",
                            gender = "Male",
                            origin = "Earth (C-137)",
                            location = "Citadel of Ricks",
                            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                        ),
                        Character(
                            id = 2,
                            name = "Morty Smith",
                            isAlive = false,
                            isKilled = false,
                            species = "Human",
                            type = "",
                            gender = "Male",
                            origin = "unknown",
                            location = "Citadel of Ricks",
                            image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
                        ),
                    ),
            )
        }
    }
}
