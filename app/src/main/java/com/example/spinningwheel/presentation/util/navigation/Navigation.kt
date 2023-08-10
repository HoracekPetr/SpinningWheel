package com.example.spinningwheel.presentation.util.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.spinningwheel.core.navigation.Screen
import com.example.spinningwheel.presentation.screens.saved.SavedWheelsScreen
import com.example.spinningwheel.presentation.screens.settings.SettingsScreen
import com.example.spinningwheel.presentation.screens.spinningwheel.SpinningWheelScreen
import com.example.spinningwheel.presentation.screens.spinningwheel.SpinningWheelViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationRoute.MAIN.route) {
        navigation(startDestination = Screen.WHEEL.route, route = NavigationRoute.MAIN.route) {
            composable(route = Screen.WHEEL.route) { entry ->
                val viewModel = entry.sharedViewModel<SpinningWheelViewModel>(navController)
                SpinningWheelScreen(
                    viewModel = viewModel,
                    onNavigate = {
                        navController.navigate(it)
                    }
                )
            }

            composable(route = Screen.SETTINGS.route) { entry ->
                val viewModel = entry.sharedViewModel<SpinningWheelViewModel>(navController)
                SettingsScreen(viewModel)
            }

            composable(route = Screen.SAVED.route) { entry ->
                val viewModel = entry.sharedViewModel<SpinningWheelViewModel>(navController)
                SavedWheelsScreen(
                    viewModel = viewModel,
                    onNavigate = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

enum class NavigationRoute(val route: String) {
    MAIN("main")
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController
): T {
    val navGraphRoute = destination.parent?.route ?: return getViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return getViewModel(viewModelStoreOwner = parentEntry)
}