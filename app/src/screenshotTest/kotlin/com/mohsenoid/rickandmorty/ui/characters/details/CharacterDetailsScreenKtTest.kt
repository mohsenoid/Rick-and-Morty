package com.mohsenoid.rickandmorty.ui.characters.details

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mohsenoid.rickandmorty.domain.characters.model.Character
import com.mohsenoid.rickandmorty.ui.theme.RickAndMortyTheme

class CharacterDetailsScreenKtTest {
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun CharacterStatusButtonAlivePreview() {
        RickAndMortyTheme {
            CharacterStatusButton(isAlive = true, isKilled = false)
        }
    }

    @Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun CharacterStatusButtonDeadPreview() {
        RickAndMortyTheme {
            CharacterStatusButton(isAlive = false, isKilled = false)
        }
    }

    @Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun CharacterStatusButtonAliveButKilledPreview() {
        RickAndMortyTheme {
            CharacterStatusButton(isAlive = true, isKilled = true)
        }
    }

    @Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun CharacterStatusButtonDeadButHealedPreview() {
        RickAndMortyTheme {
            CharacterStatusButton(isAlive = false, isKilled = false)
        }
    }

    @Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun CharacterDetailsPreview() {
        RickAndMortyTheme {
            CharacterDetails(
                Character(
                    id = 1,
                    name = "Rick Sanchez",
                    isAlive = true,
                    isKilled = false,
                    species = "Test",
                    type = "",
                    gender = "Male",
                    origin = "Earth (C-137)",
                    location = "Citadel of Ricks",
                    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                ),
            )
        }
    }
}
