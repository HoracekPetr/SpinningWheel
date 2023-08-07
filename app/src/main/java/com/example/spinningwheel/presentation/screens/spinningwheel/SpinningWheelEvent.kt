package com.example.spinningwheel.presentation.screens.spinningwheel

import com.example.spinningwheel.presentation.screens.spinningwheel.util.EntryOperation

sealed interface SpinningWheelEvent {
    object ClickedWheel : SpinningWheelEvent
    object DismissDialog : SpinningWheelEvent

    data class EnteredEntryText(val entryText: String?) : SpinningWheelEvent
    data class OnSpinningFinish(val result: String?) : SpinningWheelEvent
    data class UpdateItems(val entry: String, val operation: EntryOperation): SpinningWheelEvent
}