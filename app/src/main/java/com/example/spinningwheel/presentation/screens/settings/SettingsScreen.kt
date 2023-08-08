package com.example.spinningwheel.presentation.screens.settings

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spinningwheel.R
import com.example.spinningwheel.core.presentation.components.VerticalSpacer
import com.example.spinningwheel.core.presentation.theme.Pink40
import com.example.spinningwheel.core.presentation.theme.SPACE_16
import com.example.spinningwheel.core.presentation.theme.SPACE_2
import com.example.spinningwheel.core.presentation.theme.SPACE_28
import com.example.spinningwheel.core.presentation.theme.SPACE_4
import com.example.spinningwheel.core.presentation.theme.SPACE_6
import com.example.spinningwheel.core.presentation.util.WheelColorScheme
import com.example.spinningwheel.core.util.Constants.SCHEME_ICON_SIZE
import com.example.spinningwheel.presentation.screens.spinningwheel.SpinningWheelEvent
import com.example.spinningwheel.presentation.screens.spinningwheel.SpinningWheelViewModel
import kotlin.math.floor

@Composable
fun SettingsScreen(
    viewModel: SpinningWheelViewModel
) {
    val state by viewModel.spinningWheelState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(SPACE_16)
    ) {
        Text(
            text = stringResource(R.string.settings_title),
            style = MaterialTheme.typography.titleLarge
        )
        VerticalSpacer(space = SPACE_28)
        FontSizeSettings(
            fontSize = state.wheelFontSize,
            onValueChange = {
                viewModel.onEvent(
                    SpinningWheelEvent.ChangedFontSize(floor(it))
                )
            }
        )
        VerticalSpacer(space = SPACE_28)
        ColorSchemeSettings(
            selectedScheme = state.wheelColorScheme,
            onSchemeChange = {
                viewModel.onEvent(
                    SpinningWheelEvent.ChangedColorScheme(it)
                )
            }
        )
    }
}

@Composable
fun FontSizeSettings(
    fontSize: Float,
    onValueChange: (Float) -> Unit
) {
    Text(
        text = stringResource(id = R.string.settings_font_size, fontSize),
        style = MaterialTheme.typography.labelLarge
    )
    VerticalSpacer()
    Slider(
        value = fontSize,
        valueRange = 8f..32f,
        onValueChange = onValueChange
    )
}

@Composable
fun ColorSchemeSettings(
    selectedScheme: WheelColorScheme,
    onSchemeChange: (WheelColorScheme) -> Unit
) {
    Text(
        text = stringResource(id = R.string.settings_scheme),
        style = MaterialTheme.typography.labelLarge
    )
    VerticalSpacer()
    LazyColumn {
        items(WheelColorScheme.values().toList()) {
            ColorSchemeItem(
                scheme = it,
                selected = it == selectedScheme,
                onSchemeChange = onSchemeChange
            )
        }
    }
}

@Composable
fun ColorSchemeItem(
    modifier: Modifier = Modifier,
    scheme: WheelColorScheme,
    selected: Boolean,
    onSchemeChange: (WheelColorScheme) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (selected) Modifier.selectionBorder() else Modifier
            )
            .clip(RoundedCornerShape(SPACE_4))
            .clickable { onSchemeChange(scheme) }
            .padding(SPACE_6),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = scheme.name,
            style = MaterialTheme.typography.labelMedium
        )

        Box(modifier = Modifier.size(SCHEME_ICON_SIZE)) {
            Canvas(modifier = Modifier.size(SCHEME_ICON_SIZE)) {
                drawCircle(
                    brush = Brush.verticalGradient(colors = scheme.colorList)
                )
            }
        }
    }
}

@Composable
private fun Modifier.selectionBorder() = this.then(
    Modifier.border(
        SPACE_2,
        Pink40,
        RoundedCornerShape(SPACE_4)
    )
)