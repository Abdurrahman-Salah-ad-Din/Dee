import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.Editor
import ui.theme.editorBackgroundColor

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Dee") {
        MaterialTheme {
            var code by remember { mutableStateOf("") }
            var isRunning by remember { mutableStateOf(false) }
            Editor(
                code = code,
                isRunning = isRunning,
                onValueChanged = {
                    code = it
                }, onRunClick = {
                    isRunning = !it
                },
                modifier = Modifier.padding(top = 12.dp).fillMaxSize()
                    .background(color = editorBackgroundColor)
            )
        }
    }
}