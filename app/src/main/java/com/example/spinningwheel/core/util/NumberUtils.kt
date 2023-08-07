package com.example.spinningwheel.core.util

import kotlin.math.PI

fun Float.toRad(): Float {
    return (this * PI / 180).toFloat() // stupen = pi / 180
}