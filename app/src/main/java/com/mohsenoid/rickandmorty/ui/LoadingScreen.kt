package com.mohsenoid.rickandmorty.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mohsenoid.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
        Text("Loading...")
    }
}

@Preview
@Composable
fun LoadingScreenPreview() {
    RickAndMortyTheme(darkTheme = false) {
        LoadingScreen()
    }
}

@Preview
@Composable
fun LoadingScreenDarkPreview() {
    RickAndMortyTheme(darkTheme = true) {
        LoadingScreen()
    }
}
