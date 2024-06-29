package com.mohsenoid.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.mohsenoid.rickandmorty.ui.NavRoute
import com.mohsenoid.rickandmorty.ui.characters.details.CharacterDetailsScreen
import com.mohsenoid.rickandmorty.ui.characters.list.CharactersScreen
import com.mohsenoid.rickandmorty.ui.episodes.EpisodesScreen
import com.mohsenoid.rickandmorty.ui.theme.RickAndMortyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        AppTopBar(navController = navController)
                    },
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = NavRoute.EpisodesScreen.route,
                    ) {
                        composable(
                            route = NavRoute.EpisodesScreen.route,
                        ) {
                            EpisodesScreen(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding),
                            )
                        }

                        composable(
                            route = NavRoute.CharactersScreen.route,
                            deepLinks =
                                listOf(
                                    navDeepLink {
                                        uriPattern = NavRoute.CharactersScreen.deeplink
                                    },
                                ),
                            arguments =
                                listOf(
                                    navArgument(NavRoute.CHARACTERS_ARG) {
                                        type = NavType.StringType
                                    },
                                ),
                        ) { backStackEntry ->
                            val charactersArg =
                                backStackEntry.arguments?.getString("characters")
                            val characters =
                                charactersArg?.split(",")?.mapNotNull { it.toIntOrNull() }
                                    ?.toSet()
                                    ?: emptySet()

                            CharactersScreen(
                                navController = navController,
                                charactersIds = characters,
                                modifier = Modifier.padding(innerPadding),
                            )
                        }

                        composable(
                            route = NavRoute.CharacterDetailsScreen.route,
                            deepLinks =
                                listOf(
                                    navDeepLink {
                                        uriPattern = NavRoute.CharacterDetailsScreen.deeplink
                                    },
                                ),
                            arguments =
                                listOf(
                                    navArgument(NavRoute.CHARACTER_ARG) {
                                        type = NavType.StringType
                                    },
                                ),
                        ) { backStackEntry ->
                            val characterArg = backStackEntry.arguments?.getString("character")

                            CharacterDetailsScreen(
                                characterId = characterArg?.toIntOrNull() ?: -1,
                                modifier = Modifier.padding(innerPadding),
                            )
                        }
                    }
                }
            }
        }
    }
}
