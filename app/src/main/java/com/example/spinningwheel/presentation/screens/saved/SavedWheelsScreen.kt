package com.example.spinningwheel.presentation.screens.saved

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spinningwheel.R
import com.example.spinningwheel.core.presentation.components.HorizontalSpacer
import com.example.spinningwheel.core.presentation.components.VerticalSpacer
import com.example.spinningwheel.core.presentation.theme.NegativeRed
import com.example.spinningwheel.core.presentation.theme.SPACE_16
import com.example.spinningwheel.core.presentation.theme.SPACE_4
import com.example.spinningwheel.core.presentation.theme.SPACE_8
import com.example.spinningwheel.core.util.Constants.ICON_SIZE
import com.example.spinningwheel.core.util.Constants.SAVED_ICON_SIZE
import com.example.spinningwheel.data.local.model.SpinningWheelData
import com.example.spinningwheel.presentation.screens.spinningwheel.SpinningWheelEvent
import com.example.spinningwheel.presentation.screens.spinningwheel.SpinningWheelViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SavedWheelsScreen(
    viewModel: SpinningWheelViewModel,
    onNavigate: () -> Unit
) {
    val savedWheels by viewModel.savedWheels.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(SpinningWheelEvent.LoadAllWheels)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(SPACE_16)
    ) {
        Text(
            text = stringResource(R.string.saved_wheels_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.tertiary
        )
        VerticalSpacer(SPACE_16)
        savedWheels?.let { wheels ->
            LazyColumn(verticalArrangement = Arrangement.spacedBy(SPACE_16)) {
                items(items = wheels, key = { it.id }) {
                    SavedWheelItem(
                        modifier = Modifier.animateItemPlacement(),
                        data = it,
                        onItemClick = {
                            viewModel.onEvent(SpinningWheelEvent.LoadWheel(it.id))
                            onNavigate()
                        },
                        onDeleteClick = {
                            viewModel.onEvent(SpinningWheelEvent.DeleteWheel(it.id))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SavedWheelItem(
    modifier: Modifier = Modifier,
    data: SpinningWheelData,
    onItemClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(SPACE_4))
            .clickable { onItemClick() }
            .padding(vertical = SPACE_8),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(SAVED_ICON_SIZE),
                imageVector = Icons.Default.UploadFile,
                contentDescription = null
            )
            HorizontalSpacer()
            Text(
                text = data.wheelTitle,
                style = MaterialTheme.typography.labelLarge,
            )
        }
        IconButton(onClick = onDeleteClick) {
            Icon(
                modifier = Modifier.size(SAVED_ICON_SIZE),
                imageVector = Icons.Default.DeleteOutline,
                contentDescription = null,
                tint = NegativeRed
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SavedWheelItemPreview() {
    SavedWheelItem(data = SpinningWheelData(wheelTitle = "Něco něco"))
}