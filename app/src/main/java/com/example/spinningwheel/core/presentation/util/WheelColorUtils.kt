package com.example.spinningwheel.core.presentation.util

import androidx.compose.ui.graphics.Color
import com.example.spinningwheel.core.presentation.theme.CLASSIC_WHEEL_COLORS
import com.example.spinningwheel.core.presentation.theme.CZECH_WHEEL_COLORS
import com.example.spinningwheel.core.presentation.theme.FUNKY_WHEEL_COLORS

enum class WheelColorScheme(val colorList: List<Color>) {
    Classic(CLASSIC_WHEEL_COLORS),
    Funky(FUNKY_WHEEL_COLORS),
    Czech(CZECH_WHEEL_COLORS)
}

fun getWheelColors(scheme: WheelColorScheme, wheelDivisions: Int): List<Color> {
    val schemeListSize = scheme.colorList.size
    return buildList {
        var index = 0
        repeat(wheelDivisions) {
            if (index >= schemeListSize) {
                index = 0
            }
            add(scheme.colorList.getOrNull(index) ?: Color.White)
            index++
        }
    }
}