import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.mohsenoid.rickandmorty.App

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App(darkTheme = true)
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    App(darkTheme = true)
}
