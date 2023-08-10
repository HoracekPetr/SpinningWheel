package com.example.spinningwheel.core.navigation

sealed class Screen(val route: String) {
    object WHEEL : Screen("wheel")
    object SETTINGS : Screen("settings")
    object SAVED : Screen("saved")
}
