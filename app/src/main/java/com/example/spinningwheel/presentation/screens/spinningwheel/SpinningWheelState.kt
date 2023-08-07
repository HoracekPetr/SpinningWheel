package com.example.spinningwheel.presentation.screens.spinningwheel

data class SpinningWheelState(
    val items: List<String> = listOf(),
    val newEntry: String? = null,
    val result: String? = null,
    val isWheelSpinning: Boolean = false,
    val isResultDialogShown: Boolean = false
)
