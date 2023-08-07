package com.example.spinningwheel.presentation.screens.spinningwheel.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.spinningwheel.R

@Composable
fun SpinningWheelResultDialog(
    result: String?,
    onDismiss: () -> Unit
) {
    AlertDialog(
        title = { Text(text = stringResource(R.string.dialog_result_name, result.orEmpty())) },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.dialog_result_ok))
            }
        }
    )
}