package com.mohsenoid.rickandmorty.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = colorPrimary,
    secondary = colorSecondary,
    tertiary = colorTertiary,
    surface = colorSurface,
)

private val LightColorScheme = lightColorScheme(
    primary = colorPrimary,
    secondary = colorSecondary,
    tertiary = colorTertiary,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

val shapes = Shapes(
    extraSmall = RoundedCornerShape(2.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(32.dp),
)

@Composable
fun Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    SetStatusBarColor(darkTheme, colorScheme.primary)

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = shapes,
        typography = Typography,
        content = content,
    )
}

@Composable
expect fun SetStatusBarColor(darkTheme: Boolean, color: Color)
