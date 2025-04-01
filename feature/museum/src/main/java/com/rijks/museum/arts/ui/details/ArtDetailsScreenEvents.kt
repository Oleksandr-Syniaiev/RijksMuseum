package com.rijks.museum.arts.ui.details

import androidx.compose.runtime.Stable
import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.domain.model.UiArtDetails

@Stable
sealed class ArtDetailsScreenEvents {

    data class Loading(val objectNumber: String) : ArtDetailsScreenEvents()

    data class Loaded(val model: UiArtDetails) : ArtDetailsScreenEvents()

    data class Error(val error: DataError) : ArtDetailsScreenEvents()

    data object Back : ArtDetailsScreenEvents()

    data object HideError : ArtDetailsScreenEvents()

}
