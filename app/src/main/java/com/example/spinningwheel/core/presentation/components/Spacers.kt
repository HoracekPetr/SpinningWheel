package com.example.spinningwheel.core.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.example.spinningwheel.core.presentation.theme.SPACE_8

@Composable
fun VerticalSpacer(space: Dp = SPACE_8) {
    Spacer(modifier = Modifier.height(space))
}

@Composable
fun HorizontalSpacer(space: Dp = SPACE_8) {
    Spacer(modifier = Modifier.width(space))
}