package com.example.spinningwheel.core.presentation.util

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path

fun drawArrowPath(size: Size): Path = Path().apply {
    moveTo(x = 0f, y = size.height / 2)
    lineTo(x = size.width / 2, y = 0f)
    lineTo(x = size.width / 2, y = size.height / 4)
    lineTo(x = size.width, y = size.height / 4)
    lineTo(x = size.width, y = 3 * (size.height / 4))
    lineTo(x = size.width / 2, 3 * (size.height / 4))
    lineTo(x = size.width / 2, y = size.height)
    close()
}