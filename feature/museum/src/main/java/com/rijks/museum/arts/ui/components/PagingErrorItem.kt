package com.rijks.museum.arts.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rijks.museum.arts.R
import com.rijks.museum.core.ui.theme.RijksMuseumAppTheme

@Composable
fun PagingErrorItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(8.dp),
        horizontalAlignment = CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.pagination_failed_text),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge,
        )
        Button(
            onClick = onClick,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.try_again_pagination_action_text),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview
@Composable
private fun PagingErrorItemPreview() {
    RijksMuseumAppTheme {
        PagingErrorItem()
    }
}
