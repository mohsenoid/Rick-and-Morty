@file:OptIn(ExperimentalMaterial3Api::class)

package com.mohsenoid.rickandmorty

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
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

@Composable
fun AppTopBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val currentBackStackEntry by navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(
        initialValue = navController.currentBackStackEntry,
    )
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        },
        modifier = modifier,
        navigationIcon = {
            if (currentBackStackEntry?.destination?.route != NavRoute.EpisodesScreen.route) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.rick_morty),
                    contentDescription = "Home",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier =
                        Modifier
                            .padding(4.dp)
                            .size(40.dp),
                )
            }
        },
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AppTopBarPreview() {
    RickAndMortyTheme {
        AppTopBar(navController = rememberNavController())
    }
}
