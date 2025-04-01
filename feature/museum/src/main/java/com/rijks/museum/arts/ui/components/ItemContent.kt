package com.rijks.museum.arts.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rijks.museum.arts.common.RemoteIcon
import com.rijks.museum.core.ui.utils.EMPTY_STRING
import com.rijks.museum.domain.model.UiArtsObject

@Composable
fun ItemContent(item: UiArtsObject, onItemClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = { onItemClick(item.objectNumber) }),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            RemoteIcon(
                url = item.image,
                description = item.title,
                modifier = Modifier
                    .size(128.dp)
                    .padding(8.dp)
            )
            Text(
                text = item.title,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
        HorizontalDivider()
    }
}

@Preview
@Composable
fun ItemContentPreview() {
    ItemContent(
        item = UiArtsObject(
            id = "1",
            title = "The Night Watch",
            image = EMPTY_STRING,
            author = "Author Name",
            objectNumber = EMPTY_STRING
        ),
        onItemClick = {}
    )
}
