package com.rijks.museum.arts.ui.state

import androidx.compose.runtime.Stable
import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.domain.model.UiArtDetails

@Stable
data class ArtDetailsScreenState(
    val content: UiArtDetails,
    val isLoading: Boolean = false,
    val error: DataError? = null,
)
