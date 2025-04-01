package com.rijks.museum.arts.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AuthorHeader(author: String) {
    Text(
        text = author,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(8.dp),
        style = MaterialTheme.typography.titleMedium
    )
}

@Preview
@Composable
private fun AuthorHeaderPreview() {
    AuthorHeader(
        author = "Rembrandt",
    )
}
