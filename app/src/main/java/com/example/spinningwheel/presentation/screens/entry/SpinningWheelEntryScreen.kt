package com.example.spinningwheel.presentation.screens.entry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DoNotDisturbOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spinningwheel.R
import com.example.spinningwheel.core.presentation.components.VerticalSpacer
import com.example.spinningwheel.core.presentation.theme.NegativeRed
import com.example.spinningwheel.core.presentation.theme.PositiveGreen
import com.example.spinningwheel.core.presentation.theme.SPACE_16
import com.example.spinningwheel.core.presentation.theme.SPACE_8
import com.example.spinningwheel.presentation.screens.spinningwheel.SpinningWheelState
import com.example.spinningwheel.presentation.screens.spinningwheel.SpinningWheelViewModel
import com.example.spinningwheel.presentation.screens.spinningwheel.util.EntryOperation

@Composable
fun SpinningWheelEntryScreen(
    viewModel: SpinningWheelViewModel,
    wheelItems: List<String>,
    onEntryTextChanged: (String) -> Unit,
    onDone: () -> Unit,
    onUpdateEntry: (String, EntryOperation) -> Unit
) {
    val spinningWheelState by viewModel.spinningWheelState.collectAsStateWithLifecycle()

    SpinningWheelEntryContent(
        state = spinningWheelState,
        wheelItems = wheelItems,
        onEntryTextChanged = onEntryTextChanged,
        onUpdateEntry = onUpdateEntry,
        onDone = onDone
    )
}

@Composable
fun SpinningWheelEntryContent(
    state: SpinningWheelState,
    wheelItems: List<String>,
    onEntryTextChanged: (String) -> Unit,
    onDone: () -> Unit,
    onUpdateEntry: (String, EntryOperation) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(SPACE_16), horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.add_your_entries),
            style = MaterialTheme.typography.titleLarge
        )

        VerticalSpacer()

        LazyColumn {
            items(items = wheelItems) {
                SpinningWheelEntry(
                    entry = it,
                    entryOperation = EntryOperation.REMOVE,
                    onActionClick = onUpdateEntry
                )
            }
            item {
                SpinningWheelEntry(
                    entry = state.newEntry,
                    onActionClick = { text, entryOp ->
                        onUpdateEntry(text, entryOp)
                    },
                    onTextChange = onEntryTextChanged,
                    onDone = onDone,
                    icon = Icons.Default.AddCircle,
                    entryOperation = EntryOperation.ADD,
                    iconTint = PositiveGreen
                )
            }
        }
    }
}

@Composable
fun SpinningWheelEntry(
    entry: String? = null,
    icon: ImageVector = Icons.Default.DoNotDisturbOn,
    iconTint: Color = NegativeRed,
    entryOperation: EntryOperation,
    onTextChange: ((String) -> Unit)? = null,
    onDone: (() -> Unit)? = null,
    onActionClick: (String, EntryOperation) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = SPACE_8),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (entryOperation) {
            EntryOperation.ADD -> OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White
                ),
                value = entry.orEmpty(),
                onValueChange = {
                    if (onTextChange != null) {
                        onTextChange(it)
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (onDone != null) {
                            onDone()
                        }
                    }
                )
            )

            EntryOperation.REMOVE -> Text(
                text = entry.orEmpty(),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        IconButton(onClick = {
            entry?.let {
                onActionClick(it, entryOperation)
            }
        }) {
            Icon(
                imageVector = icon, tint = iconTint, contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SpinningWheelEntryScreenPreview() {
    SpinningWheelEntryContent(
        wheelItems = listOf("test", "test 2", "test 3"),
        state = SpinningWheelState(),
        onUpdateEntry = { _, _ -> },
        onEntryTextChanged = {},
        onDone = {}
    )
}

