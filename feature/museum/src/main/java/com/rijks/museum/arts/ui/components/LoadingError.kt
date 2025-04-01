package com.rijks.museum.arts.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rijks.museum.arts.R
import com.rijks.museum.core.utils.errors.DataError

@Composable
fun LoadingError(
    error: DataError,
    onClick: () -> Unit,
) {
    when (error) {
        DataError.Network.NO_INTERNET ->
            TextError(
                stringResource(R.string.no_internet_error),
                onClick,
            )

        DataError.Network.SERVER_ERROR ->
            TextError(
                stringResource(R.string.server_error),
                onClick,
            )

        DataError.Network.UNKNOWN ->
            TextError(
                stringResource(R.string.unknown_error),
                onClick,
            )
    }
}

@Composable
private fun TextError(
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Button(
                onClick = onClick,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
            ) {
                Text(
                    text = stringResource(R.string.retry_button_text),
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}

@Preview
@Composable
fun LoadingErrorPreview() {
    LoadingError(
        error = DataError.Network.NO_INTERNET,
        onClick = {},
    )
}
