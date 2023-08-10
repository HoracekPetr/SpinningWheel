package com.example.spinningwheel.presentation.screens.spinningwheel

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SdCard
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spinningwheel.R
import com.example.spinningwheel.core.navigation.Screen
import com.example.spinningwheel.core.presentation.components.TransparentTextField
import com.example.spinningwheel.core.presentation.components.VerticalSpacer
import com.example.spinningwheel.core.presentation.components.WheelButton
import com.example.spinningwheel.core.presentation.theme.SPACE_16
import com.example.spinningwheel.core.presentation.theme.SPACE_32
import com.example.spinningwheel.core.presentation.theme.SPACE_8
import com.example.spinningwheel.core.util.Constants.ICON_SIZE
import com.example.spinningwheel.core.util.Constants.MIN_WHEEL_ENTRIES
import com.example.spinningwheel.core.util.Constants.SHEET_PEAK_HEIGHT
import com.example.spinningwheel.data.local.model.SpinningWheelData
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
                wheelItems = spinningWheelState.data.items,
                onUpdateEntry = { entry, operation, index ->
                    viewModel.onEvent(SpinningWheelEvent.UpdateItems(entry, operation, index))
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
                onTitleFocusLost = {
                    viewModel.onEvent(SpinningWheelEvent.TitleFocusLost)
                },
                onRemove = {
                    viewModel.onEvent(SpinningWheelEvent.RemoveItem(it))
                },
                onSave = {
                    viewModel.onEvent(SpinningWheelEvent.SaveWheel(spinningWheelState.data))
                },
                onClear = {
                    viewModel.onEvent(SpinningWheelEvent.ClearWheel)
                },
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
    onTitleFocusLost: () -> Unit,
    onRemove: (String?) -> Unit,
    onSave: () -> Unit,
    onClear: () -> Unit,
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
                items = state.data.items,
                fontSize = state.data.wheelFontSize,
                colorScheme = state.data.wheelColorScheme,
                isSpinning = state.isWheelSpinning,
                onWheelClick = onWheelClick,
                onAnimationFinish = onSpinningFinish
            )


            AnimatedVisibility(state.data.items.size >= MIN_WHEEL_ENTRIES) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    VerticalSpacer(space = SPACE_32)

                    TransparentTextField(
                        modifier = Modifier.onFocusChanged {
                            if (!it.isFocused) {
                                onTitleFocusLost()
                            }
                        },
                        text = state.data.wheelTitle,
                        onTextChanged = onChangedTitle,
                        onDone = {
                            focusManager?.clearFocus()
                        }
                    )

                    VerticalSpacer(space = SPACE_8)

                    Row(
                        Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(
                            SPACE_8, Alignment.CenterHorizontally
                        )
                    ) {
                        WheelButton(
                            text = stringResource(R.string.save_wheel),
                            onClick = onSave,
                            enabled = state.data.wheelTitle.isNotEmpty(),
                            isLoading = state.isLoading
                        )
                        WheelButton(
                            text = stringResource(R.string.clear_wheel),
                            onClick = onClear
                        )
                    }
                }
            }

            if (state.isResultDialogShown) {
                SpinningWheelResultDialog(
                    title = state.data.wheelTitle,
                    result = state.result,
                    onDismiss = onDismiss,
                    onRemove = onRemove
                )
            }
        }

        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(ICON_SIZE),
            onClick = { onNavigate(Screen.SETTINGS.route) }
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null
            )
        }

        IconButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(ICON_SIZE),
            onClick = { onNavigate(Screen.SAVED.route) }
        ) {
            Icon(
                imageVector = Icons.Default.SdCard,
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
            data = SpinningWheelData(
                items = listOf("test", "test 2", "test 3")
            )
        ),
        onNavigate = {},
        onWheelClick = {},
        onSpinningFinish = {},
        onDismiss = {},
        onChangedTitle = {},
        onTitleFocusLost = {},
        onRemove = {},
        onSave = {},
        onClear = {},
        focusManager = null
    )
}

