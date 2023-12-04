package com.mohsenoid.rickandmorty

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(darkTheme = isDarkModeOn())
        }
    }

    private fun isDarkModeOn(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }
}


@Preview
@Composable
fun AppAndroidDarkPreview() {
    App(darkTheme = true)
}

@Preview
@Composable
fun AppAndroidLightPreview() {
    App(darkTheme = false)
}
