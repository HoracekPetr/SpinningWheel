package com.example.spinningwheel.core.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

fun <T> MutableStateFlow<T>.updateState(block: T.() -> T) {
    this.update {
        this.value.block()
    }
}