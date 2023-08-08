package com.example.spinningwheel.presentation.screens.spinningwheel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spinningwheel.core.navigation.Screen
import com.example.spinningwheel.core.presentation.components.TransparentTextField
import com.example.spinningwheel.core.presentation.components.VerticalSpacer
import com.example.spinningwheel.core.presentation.theme.SPACE_16
import com.example.spinningwheel.core.presentation.theme.SPACE_28
import com.example.spinningwheel.core.presentation.theme.SPACE_32
import com.example.spinningwheel.core.presentation.theme.SPACE_8
import com.example.spinningwheel.core.util.Constants.ICON_BUTTON_SIZE
import com.example.spinningwheel.core.util.Constants.SHEET_PEAK_HEIGHT
import com.example.spinningwheel.presentation.screens.entry.SpinningWheelEntryScreen
import com.example.spinningwheel.presentation.screens.spinningwheel.components.SpinningWheel
import com.example.spinningwheel.presentation.screens.spinningwheel.components.SpinningWheelResultDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinningWheelScreen(
    viewModel: SpinningWheelViewModel,
    onNavigate: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    val spinningWheelState by viewModel.spinningWheelState.collectAsStateWithLifecycle()
    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            confirmValueChange = {
                it != SheetValue.Hidden
            }
        )
    )

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetContent = {
            SpinningWheelEntryScreen(
                viewModel = viewModel,
                wheelItems = spinningWheelState.items,
                onUpdateEntry = { entry, operation ->
                    viewModel.onEvent(SpinningWheelEvent.UpdateItems(entry, operation))
                },
                onEntryTextChanged = {
                    viewModel.onEvent(SpinningWheelEvent.EnteredEntryText(it))
                },
                onDone = {
                    focusManager.clearFocus()
                }
            )
        },
        sheetPeekHeight = SHEET_PEAK_HEIGHT,
        sheetShape = RoundedCornerShape(topStart = SPACE_16, topEnd = SPACE_16),
        content = { paddingValues ->
            SpinningWheelScreenContent(
                paddingValues = paddingValues,
                focusManager = focusManager,
                state = spinningWheelState,
                onNavigate = onNavigate,
                onWheelClick = { viewModel.onEvent(SpinningWheelEvent.ClickedWheel) },
                onSpinningFinish = { viewModel.onEvent(SpinningWheelEvent.OnSpinningFinish(it)) },
                onDismiss = { viewModel.onEvent(SpinningWheelEvent.DismissDialog) },
                onChangedTitle = {
                    viewModel.onEvent(SpinningWheelEvent.EnteredTitle(it))
                }
            )
        }
    )
}

@Composable
fun SpinningWheelScreenContent(
    paddingValues: PaddingValues,
    focusManager: FocusManager?,
    state: SpinningWheelState,
    onNavigate: (String) -> Unit,
    onWheelClick: () -> Unit,
    onSpinningFinish: (String?) -> Unit,
    onDismiss: () -> Unit,
    onChangedTitle: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusManager?.clearFocus()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpinningWheel(
                modifier = Modifier.size(400.dp),
                items = state.items,
                fontSize = state.wheelFontSize,
                colorScheme = state.wheelColorScheme,
                isSpinning = state.isWheelSpinning,
                onWheelClick = onWheelClick,
                onAnimationFinish = onSpinningFinish
            )

            VerticalSpacer(space = SPACE_32)

            TransparentTextField(
                text = state.wheelTitle,
                onTextChanged = onChangedTitle,
                onDone = {
                    focusManager?.clearFocus()
                }
            )

            if (state.isResultDialogShown) {
                SpinningWheelResultDialog(
                    title = state.wheelTitle,
                    result = state.result,
                    onDismiss = onDismiss
                )
            }
        }

        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(ICON_BUTTON_SIZE),
            onClick = { onNavigate(Screen.SETTINGS.route) }
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SpinningWheelScreenPreview() {
    SpinningWheelScreenContent(
        paddingValues = PaddingValues(SPACE_8),
        state = SpinningWheelState(
            items = listOf("test", "test 2", "test 3")
        ),
        onNavigate = {},
        onWheelClick = {},
        onSpinningFinish = {},
        onDismiss = {},
        onChangedTitle = {},
        focusManager = null
    )
}

