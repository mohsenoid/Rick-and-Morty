package com.mohsenoid.rickandmorty.view

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val colorPrimary = Color(0xFF008577)
private val colorPrimaryVariant = Color(0xFF00574B)
private val colorSecondary = Color(0xFFD81B60)
private val colorSurface = Color(0xFF424242)

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
