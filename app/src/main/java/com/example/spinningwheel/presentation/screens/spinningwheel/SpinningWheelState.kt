package com.example.spinningwheel.presentation.screens.spinningwheel

import com.example.spinningwheel.data.local.model.SpinningWheelData

data class SpinningWheelState(
    val data: SpinningWheelData = SpinningWheelData(),
    val newEntry: String? = null,
    val result: String? = null,
    val isLoading: Boolean = false,
    val isWheelSpinning: Boolean = false,
    val isResultDialogShown: Boolean = false
)
