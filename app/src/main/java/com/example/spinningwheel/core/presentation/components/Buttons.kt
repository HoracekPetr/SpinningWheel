package com.example.spinningwheel.core.presentation.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.spinningwheel.R

@Composable
fun WheelButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    OutlinedButton(onClick = onClick, enabled = enabled) {
        when(isLoading){
            true -> {
                CircularProgressIndicator()
            }
            false -> {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
