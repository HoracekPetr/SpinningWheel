package com.example.spinningwheel.presentation.screens.spinningwheel.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.spinningwheel.R

@Composable
fun SpinningWheelResultDialog(
    title: String,
    result: String?,
    onDismiss: () -> Unit
) {
    val resultTitle = if (title.isNotEmpty()) "$title: ${result.orEmpty()}" else
        stringResource(
            R.string.dialog_result_name,
            result.orEmpty()
        )

    AlertDialog(
        title = { Text(text = resultTitle) },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.dialog_result_ok))
            }
        }
    )
}