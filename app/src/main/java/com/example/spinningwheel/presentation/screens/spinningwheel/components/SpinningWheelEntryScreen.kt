package com.example.spinningwheel.presentation.screens.spinningwheel.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DoNotDisturbOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.spinningwheel.R
import com.example.spinningwheel.core.presentation.theme.NegativeRed
import com.example.spinningwheel.core.presentation.theme.PositiveGreen
import com.example.spinningwheel.core.presentation.theme.SPACE_8
import com.example.spinningwheel.presentation.screens.spinningwheel.util.EntryOperation

@Composable
fun SpinningWheelEntryScreen(
    wheelItems: List<String>,
    onUpdateEntry: (String, EntryOperation) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(SPACE_8),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.add_your_entries),
            style = MaterialTheme.typography.titleLarge
        )
        LazyColumn {
            items(items = wheelItems) {
                SpinningWheelEntry(
                    entry = it,
                    entryOperation = EntryOperation.REMOVE,
                    onActionClick = onUpdateEntry
                )
            }
        }
        SpinningWheelEntry(
            onActionClick = onUpdateEntry,
            icon = Icons.Default.AddCircle,
            entryOperation = EntryOperation.ADD,
            iconTint = PositiveGreen
        )
    }
}

@Composable
fun SpinningWheelEntry(
    entry: String? = null,
    icon: ImageVector = Icons.Default.DoNotDisturbOn,
    iconTint: Color = NegativeRed,
    entryOperation: EntryOperation,
    onActionClick: (String, EntryOperation) -> Unit
) {
    var entryText by remember {
        mutableStateOf(entry)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = SPACE_8),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = entryText.orEmpty(), onValueChange = { entryText = it }
        )

        IconButton(onClick = {
            entryText?.let {
                onActionClick(it, entryOperation)
            }
        }) {
            Icon(
                imageVector = icon,
                tint = iconTint,
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SpinningWheelEntryScreenPreview() {
    SpinningWheelEntryScreen(
        wheelItems = listOf("test", "test 2", "test 3"),
        onUpdateEntry = { _, _ ->  }
    )
}

