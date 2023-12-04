package com.mohsenoid.rickandmorty

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mohsenoid.rickandmorty.character.data.CharacterRemoteDataSource
import com.mohsenoid.rickandmorty.character.data.CharacterRepositoryImpl
import com.mohsenoid.rickandmorty.character.presentation.CharacterScreen
import com.mohsenoid.rickandmorty.character.presentation.CharacterViewModel
import com.mohsenoid.rickandmorty.episode.data.EpisodeRemoteDataSource
import com.mohsenoid.rickandmorty.episode.data.EpisodeRepositoryImpl
import com.mohsenoid.rickandmorty.episode.presentation.EpisodeScreen
import com.mohsenoid.rickandmorty.episode.presentation.EpisodeViewModel
import com.mohsenoid.rickandmorty.episodes.data.EpisodesRemoteDataSource
import com.mohsenoid.rickandmorty.episodes.data.EpisodesRepositoryImpl
import com.mohsenoid.rickandmorty.episodes.presentation.EpisodesScreen
import com.mohsenoid.rickandmorty.episodes.presentation.EpisodesViewModel
import com.mohsenoid.rickandmorty.theme.Theme
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

@Composable
fun App(darkTheme: Boolean) {
    PreComposeApp {
        Theme(darkTheme = darkTheme) {
            val snackbarHostState = remember { SnackbarHostState() }
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                snackbarHost = { SnackbarHost(snackbarHostState) },
                topBar = {
                    Actionbar(
                        title = "Rick and Morty",
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
            ) { paddingValues ->
                val navigator = rememberNavigator()
                NavHost(
                    modifier = Modifier.padding(paddingValues),
                    navigator = navigator,
                    navTransition = NavTransition(),
                    initialRoute = NavRoute.EpisodesScreen.route,
                ) {
                    scene(
                        route = NavRoute.EpisodesScreen.route,
                        navTransition = NavTransition(),
                    ) {
                        val episodesViewModel =
                            getViewModel(
                                key = Unit,
                                factory = viewModelFactory {
                                    EpisodesViewModel(
                                        repository = EpisodesRepositoryImpl(remote = EpisodesRemoteDataSource()),
                                    )
                                },
                            )
                        val uiState by episodesViewModel.uiState.collectAsState()
                        LaunchedEffect(episodesViewModel) {
                            episodesViewModel.updateEpisodes()
                        }

                        EpisodesScreen(
                            uiState = uiState,
                            modifier = Modifier.fillMaxSize(),
                            onEpisodeClicked = { episodeId ->
                                navigator.navigate(
                                    route = NavRoute.EpisodeScreen.createRoute(episodeId),
                                )
                            },
                        )
                    }
                    scene(
                        route = NavRoute.EpisodeScreen.route,
                        navTransition = NavTransition(),
                    ) { backStackEntry ->
                        val episodeId: Int? = backStackEntry.path<Int>(NavRoute.EpisodeScreen.EPISODE_ID)
                        if (episodeId == null) {
                            navigator.navigate(route = NavRoute.EpisodesScreen.route)
                            return@scene
                        }
                        val episodeViewModel =
                            getViewModel(
                                key = Unit,
                                factory = viewModelFactory {
                                    EpisodeViewModel(
                                        episodeId = episodeId,
                                        repository = EpisodeRepositoryImpl(remote = EpisodeRemoteDataSource()),
                                    )
                                },
                            )
                        val uiState by episodeViewModel.uiState.collectAsState()
                        LaunchedEffect(episodeViewModel) {
                            episodeViewModel.updateEpisode()
                        }

                        EpisodeScreen(
                            uiState = uiState,
                            modifier = Modifier.fillMaxSize(),
                            onCharacterClicked = { characterId ->
                                navigator.navigate(
                                    route = NavRoute.CharacterScreen.createRoute(characterId),
                                )
                            },
                        )
                    }
                    scene(
                        route = NavRoute.CharacterScreen.route,
                        navTransition = NavTransition(),
                    ) { backStackEntry ->
                        val characterId: Int? = backStackEntry.path<Int>(NavRoute.CharacterScreen.CHARACTER_ID)
                        if (characterId == null) {
                            navigator.navigate(route = NavRoute.EpisodesScreen.route)
                            return@scene
                        }
                        val characterViewModel =
                            getViewModel(
                                key = Unit,
                                factory = viewModelFactory {
                                    CharacterViewModel(
                                        characterId = characterId,
                                        repository = CharacterRepositoryImpl(remote = CharacterRemoteDataSource()),
                                    )
                                },
                            )
                        val uiState by characterViewModel.uiState.collectAsState()
                        LaunchedEffect(characterViewModel) {
                            characterViewModel.updateCharacter()
                        }

                        CharacterScreen(
                            uiState = uiState,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Actionbar(title: String, modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        modifier = modifier,
        navigationIcon = {
        },
        actions = {
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
    )
}
