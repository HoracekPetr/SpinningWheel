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
                    entryOperation = event.operation,
                    index = event.index
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
                        wheelTitle = event.title
                    )
                }
            }
            is SpinningWheelEvent.TitleFocusLost -> {
                _spinningWheelState.updateState {
                    copy(
                        wheelTitle = _spinningWheelState.value.wheelTitle.trim()
                    )
                }
            }

            is SpinningWheelEvent.RemoveItem -> {
                removeResultItem(event.result)

                _spinningWheelState.updateState {
                    copy(
                        result = null,
                        isResultDialogShown = false
                    )
                }
            }
        }
    }

    private fun updateSpinningWheelList(
        entry: String,
        entryOperation: EntryOperation,
        index: Int?
    ) {
        val items = _spinningWheelState.value.items

        val newList = buildList {
            val oldList = when (entryOperation) {
                EntryOperation.ADD -> items + entry
                EntryOperation.REMOVE -> {
                    val mutableItems = items.toMutableList()
                    index?.let {
                        mutableItems.removeAt(index)
                    }
                    mutableItems.toList()
                }
            }
            this.addAll(oldList)
        }

        if (entryOperation == EntryOperation.ADD) {
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

    private fun removeResultItem(result: String?){
        val items = _spinningWheelState.value.items

        val newList = buildList {
            val oldList = items.find { it == result }?.let {
                items - it
            } ?: items
            this.addAll(oldList)
        }

        _spinningWheelState.updateState {
            copy(
                items = newList
            )
        }
    }
}