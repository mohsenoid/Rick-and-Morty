package com.mohsenoid.rickandmorty.view

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColors = darkColors(
    primary = colorPrimary,
    primaryVariant = colorPrimaryVariant,
    secondary = colorSecondary,
    surface = colorSurface,
)

private val LightColors = lightColors(
    primary = colorPrimary,
    primaryVariant = colorPrimaryVariant,
    secondary = colorSecondary,
)

@Composable
fun RickAndMortyTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        content = content,
    )
}
