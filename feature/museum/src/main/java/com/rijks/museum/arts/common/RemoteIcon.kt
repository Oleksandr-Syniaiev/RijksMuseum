package com.rijks.museum.arts.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun RemoteIcon(
    url: String,
    description: String,
    modifier: Modifier = Modifier,
    tint: Color = Color.Transparent
) {
    var isImageLoading by remember { mutableStateOf(true) }

    Box(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (isImageLoading) Color.LightGray else Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = url,
            contentDescription = description,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize(),
            onSuccess = { isImageLoading = false },
            onLoading = { isImageLoading = true },
            onError = { isImageLoading = false },
            colorFilter = ColorFilter.tint(tint)
        )
    }
}
