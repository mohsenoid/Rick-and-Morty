package com.mohsenoid.rickandmorty.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohsenoid.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun NoConnectionErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Filled.WifiOff,
            contentDescription = "No Connection Error",
            modifier = Modifier.size(48.dp),
            tint = Color.Red,
        )
        Text("No Connection Error!")
    }
}

@Preview
@Composable
fun NoConnectionErrorScreenPreview() {
    RickAndMortyTheme(darkTheme = false) {
        NoConnectionErrorScreen()
    }
}

@Preview
@Composable
fun NoConnectionErrorScreenDarkPreview() {
    RickAndMortyTheme(darkTheme = true) {
        NoConnectionErrorScreen()
    }
}
