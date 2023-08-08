package com.example.spinningwheel.presentation.screens.spinningwheel

import androidx.lifecycle.ViewModel
import com.example.spinningwheel.core.util.updateState
import com.example.spinningwheel.presentation.screens.spinningwheel.util.EntryOperation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SpinningWheelViewModel : ViewModel() {

    private val _spinningWheelState = MutableStateFlow(SpinningWheelState())
    val spinningWheelState = _spinningWheelState.asStateFlow()

    fun onEvent(event: SpinningWheelEvent) {
        when (event) {
            is SpinningWheelEvent.ClickedWheel -> {
                _spinningWheelState.updateState {
                    copy(isWheelSpinning = true)
                }
            }

            is SpinningWheelEvent.OnSpinningFinish -> {
                _spinningWheelState.updateState {
                    copy(
                        result = event.result,
                        isWheelSpinning = false,
                        isResultDialogShown = true
                    )
                }
            }

            is SpinningWheelEvent.DismissDialog -> {
                _spinningWheelState.updateState {
                    copy(
                        result = null,
                        isResultDialogShown = false
                    )
                }
            }

            is SpinningWheelEvent.UpdateItems -> {
                updateSpinningWheelList(
                    entry = event.entry,
                    entryOperation = event.operation
                )
            }

            is SpinningWheelEvent.EnteredEntryText -> {
                _spinningWheelState.updateState {
                    copy(
                        newEntry = event.entryText
                    )
                }
            }

            is SpinningWheelEvent.ChangedFontSize -> {
                _spinningWheelState.updateState {
                    copy(
                        wheelFontSize = event.fontSize
                    )
                }
            }

            is SpinningWheelEvent.ChangedColorScheme -> {
                _spinningWheelState.updateState {
                    copy(
                        wheelColorScheme = event.colorScheme
                    )
                }
            }

            is SpinningWheelEvent.EnteredTitle -> {
                _spinningWheelState.updateState {
                    copy(
                        wheelTitle = event.title.trim()
                    )
                }
            }
        }
    }

    private fun updateSpinningWheelList(entry: String, entryOperation: EntryOperation) {
        val items = _spinningWheelState.value.items

        val newList = buildList {
            val oldList = when (entryOperation) {
                EntryOperation.ADD -> items + entry
                EntryOperation.REMOVE -> items.find { it == entry }?.let {
                    items - it
                } ?: items
            }
            this.addAll(oldList)
        }

        if(entryOperation == EntryOperation.ADD){
            _spinningWheelState.updateState {
                copy(
                    newEntry = null
                )
            }
        }

        _spinningWheelState.updateState {
            copy(
                items = newList
            )
        }
    }
}