package com.example.spinningwheel.presentation.screens.spinningwheel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spinningwheel.core.util.updateState
import com.example.spinningwheel.data.local.model.SpinningWheelData
import com.example.spinningwheel.data.repository.WheelRepository
import com.example.spinningwheel.data.repository.WheelRepositoryImpl
import com.example.spinningwheel.presentation.screens.spinningwheel.util.EntryOperation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SpinningWheelViewModel(
    private val repository: WheelRepository
) : ViewModel() {

    private val _savedWheels = MutableStateFlow<List<SpinningWheelData>?>(null)
    val savedWheels = _savedWheels.asStateFlow()

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
                        data = _spinningWheelState.value.data.copy(
                            wheelFontSize = event.fontSize
                        )
                    )
                }
            }

            is SpinningWheelEvent.ChangedColorScheme -> {
                _spinningWheelState.updateState {
                    copy(
                        data = _spinningWheelState.value.data.copy(
                            wheelColorScheme = event.colorScheme
                        )
                    )
                }
            }

            is SpinningWheelEvent.EnteredTitle -> {
                _spinningWheelState.updateState {
                    copy(
                        data = _spinningWheelState.value.data.copy(
                            wheelTitle = event.title
                        )
                    )
                }
            }

            is SpinningWheelEvent.TitleFocusLost -> {
                _spinningWheelState.updateState {
                    copy(
                        data = _spinningWheelState.value.data.copy(
                            wheelTitle = _spinningWheelState.value.data.wheelTitle.trim()
                        )
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

            is SpinningWheelEvent.LoadAllWheels -> {
                viewModelScope.launch {
                    repository.getAllWheelData().collect { wheelData ->
                        _savedWheels.update { wheelData }
                    }
                }
            }

            is SpinningWheelEvent.LoadWheel -> {
                viewModelScope.launch {
                    val data = repository.getWheelData(event.id)

                    _spinningWheelState.updateState {
                        copy(
                            data = data
                        )
                    }
                }
            }

            is SpinningWheelEvent.SaveWheel -> {
                viewModelScope.launch {

                    _spinningWheelState.updateState {
                        copy(
                            isLoading = true
                        )
                    }

                    repository.insertWheelData(event.data)

                    _spinningWheelState.updateState {
                        copy(
                            isLoading = false
                        )
                    }
                }
            }

            is SpinningWheelEvent.DeleteWheel -> {
                viewModelScope.launch {
                    repository.deleteWheelData(event.id)
                }
            }

            is SpinningWheelEvent.ClearWheel -> {
                _spinningWheelState.updateState {
                    copy(
                        data = SpinningWheelData()
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
        val items = _spinningWheelState.value.data.items

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
                data = _spinningWheelState.value.data.copy(
                    items = newList
                )
            )
        }
    }

    private fun removeResultItem(result: String?) {
        val items = _spinningWheelState.value.data.items

        val newList = buildList {
            val oldList = items.find { it == result }?.let {
                items - it
            } ?: items
            this.addAll(oldList)
        }

        _spinningWheelState.updateState {
            copy(
                data = _spinningWheelState.value.data.copy(
                    items = newList
                )
            )
        }
    }
}