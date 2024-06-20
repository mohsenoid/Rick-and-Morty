package com.mohsenoid.rickandmorty.ui

sealed class NavRoute(val endpoint: String, vararg args: String) {
    val deeplink: String = BASE_URL + endpoint + args.joinToString(separator = "&") { "{$it}" }
    val route = endpoint + args.joinToString(separator = "&") { "{$it}" }

    data object EpisodesScreen : NavRoute(endpoint = "episodes/")

    data object CharactersScreen : NavRoute(endpoint = "characters/", CHARACTERS_ARG)

    data object CharacterDetailsScreen : NavRoute(endpoint = "character/", CHARACTER_ARG)

    companion object {
        const val BASE_URL = "https://rickandmorty.com/"
        const val CHARACTERS_ARG = "characters"
        const val CHARACTER_ARG = "character"
    }
}
