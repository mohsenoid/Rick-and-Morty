package com.mohsenoid.rickandmorty

sealed interface NavRoute {
    val route: String

    data object EpisodesScreen : NavRoute {
        override val route: String = "/episodes_screen"
    }

    data object EpisodeScreen : NavRoute {
        const val EPISODE_ID = "id"
        override val route = "/episode_screen/{$EPISODE_ID}"

        fun createRoute(episodeId: Int) = route.replace("{$EPISODE_ID}", episodeId.toString())
    }

    data object CharacterScreen : NavRoute {
        const val CHARACTER_ID = "id"
        override val route = "/character_screen/{$CHARACTER_ID}"
        fun createRoute(episodeId: Int) = route.replace("{$CHARACTER_ID}", episodeId.toString())
    }
}
