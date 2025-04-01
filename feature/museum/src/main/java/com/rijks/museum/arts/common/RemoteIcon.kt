package com.rijks.museum.arts.common

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.rijks.museum.core.ui.theme.icons.Icons
import com.rijks.museum.core.ui.utils.EMPTY_STRING

@Composable
fun RemoteIcon(
    url: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .build(),
        contentDescription = description,
        contentScale = ContentScale.FillBounds,
        placeholder = ColorPainter(Color.DarkGray),
        error = rememberVectorPainter(Icons.NoImagePlaceholder),
        modifier = modifier
            .clip(RoundedCornerShape(16.dp)),
    )
}

@Preview
@Composable
fun RemoteIconPreview() {
    RemoteIcon(
        url = EMPTY_STRING,
        description = EMPTY_STRING,
        modifier = Modifier
            .size(128.dp)
    )
}
