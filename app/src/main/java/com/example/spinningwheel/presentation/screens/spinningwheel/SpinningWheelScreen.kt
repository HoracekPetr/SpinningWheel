package com.example.spinningwheel.presentation.screens.spinningwheel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spinningwheel.core.presentation.theme.SPACE_16
import com.example.spinningwheel.core.presentation.theme.SPACE_8
import com.example.spinningwheel.core.util.Constants.SHEET_PEAK_HEIGHT
import com.example.spinningwheel.presentation.screens.spinningwheel.components.SpinningWheel
import com.example.spinningwheel.presentation.screens.spinningwheel.components.SpinningWheelResultDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinningWheelScreen(
    viewModel: SpinningWheelViewModel = viewModel(),
) {
    val spinningWheelState by viewModel.spinningWheelState.collectAsStateWithLifecycle()

    val bottomSheetState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetContent = {
            SpinningWheelEntryScreen(
                wheelItems = spinningWheelState.items,
                onUpdateEntry = { entry, operation ->
                    viewModel.onEvent(SpinningWheelEvent.UpdateItems(entry, operation))
                },
                onEntryTextChanged = {
                    viewModel.onEvent(SpinningWheelEvent.EnteredEntryText(it))
                }
            )
        },
        sheetPeekHeight = SHEET_PEAK_HEIGHT,
        sheetShape = RoundedCornerShape(topStart = SPACE_16, topEnd = SPACE_16),
        content = { paddingValues ->
            SpinningWheelScreenContent(
                paddingValues = paddingValues,
                state = spinningWheelState,
                onWheelClick = { viewModel.onEvent(SpinningWheelEvent.ClickedWheel) },
                onSpinningFinish = { viewModel.onEvent(SpinningWheelEvent.OnSpinningFinish(it)) },
                onDismiss = { viewModel.onEvent(SpinningWheelEvent.DismissDialog) }
            )
        }
    )
}

@Composable
fun SpinningWheelScreenContent(
    paddingValues: PaddingValues,
    state: SpinningWheelState,
    onWheelClick: () -> Unit,
    onSpinningFinish: (String?) -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        SpinningWheel(
            items = state.items,
            isSpinning = state.isWheelSpinning,
            onWheelClick = onWheelClick,
            onAnimationFinish = onSpinningFinish
        )

        if (state.isResultDialogShown) {
            SpinningWheelResultDialog(
                result = state.result,
                onDismiss = onDismiss
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
        onWheelClick = {},
        onSpinningFinish = {},
        onDismiss = {}
    )
}

