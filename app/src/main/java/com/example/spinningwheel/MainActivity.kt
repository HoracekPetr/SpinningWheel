package com.example.spinningwheel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spinningwheel.presentation.screens.spinningwheel.SpinningWheelScreen
import com.example.spinningwheel.core.presentation.theme.SpinningWheelTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpinningWheelTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SpinningWheelScreen()
/*                    Box(Modifier.fillMaxSize()) {
                        Canvas(
                            modifier = Modifier
                                .size(50.dp)
                        )
                        {
                            drawPath(
                                path = Path().apply {
                                    moveTo(x = 0f, y = this@Canvas.size.height / 2)
                                    lineTo(x = this@Canvas.size.width / 2, y = 0f)
                                    lineTo(x = this@Canvas.size.width / 2, y = this@Canvas.size.height)
                                    close()
                                },
                                color = Color.Red
                            )
                            drawRoundRect(Color.Red)
                        }
                    }*/
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SpinningWheelTheme {
        Greeting("Android")
    }
}

val items = listOf(
    "Petr", "Markéta", "Lukáš", "Martin", "Kristýna"
)