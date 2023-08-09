package com.example.spinningwheel.presentation.screens.spinningwheel

import com.example.spinningwheel.core.presentation.util.WheelColorScheme
import com.example.spinningwheel.presentation.screens.spinningwheel.util.EntryOperation

sealed interface SpinningWheelEvent {
    object ClickedWheel : SpinningWheelEvent
    object DismissDialog : SpinningWheelEvent
    object TitleFocusLost: SpinningWheelEvent

    data class EnteredTitle(val title: String) : SpinningWheelEvent
    data class EnteredEntryText(val entryText: String?) : SpinningWheelEvent
    data class ChangedFontSize(val fontSize: Float) : SpinningWheelEvent
    data class ChangedColorScheme(val colorScheme: WheelColorScheme) : SpinningWheelEvent
    data class OnSpinningFinish(val result: String?) : SpinningWheelEvent
    data class RemoveItem(val result: String?) : SpinningWheelEvent
    data class UpdateItems(
        val entry: String,
        val operation: EntryOperation,
        val index: Int? = null
    ) : SpinningWheelEvent
}