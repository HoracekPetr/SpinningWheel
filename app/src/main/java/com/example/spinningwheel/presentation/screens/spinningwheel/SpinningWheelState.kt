package com.example.spinningwheel.presentation.screens.spinningwheel

import com.example.spinningwheel.core.presentation.util.WheelColorScheme

data class SpinningWheelState(
    val items: List<String> = listOf(),
    val wheelFontSize: Float = 16f,
    val wheelColorScheme: WheelColorScheme = WheelColorScheme.Classic,
    val newEntry: String? = null,
    val result: String? = null,
    val isWheelSpinning: Boolean = false,
    val isResultDialogShown: Boolean = false
)
