package com.rijks.museum.arts.ui.details.utils

import com.rijks.museum.arts.ui.details.ArtDetailsScreenEvents
import com.rijks.museum.arts.ui.state.ArtDetailsScreenState
import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.domain.model.UiArtDetails

fun loadedEventToState(
    previousState: ArtDetailsScreenState,
    content: UiArtDetails,
): Pair<ArtDetailsScreenEvents.Loaded, ArtDetailsScreenState> =
    ArtDetailsScreenEvents.Loaded(content) to
        previousState.copy(
            isLoading = false,
            error = null,
            content = content,
        )

fun errorEventToState(
    previousState: ArtDetailsScreenState,
    error: DataError,
): Pair<ArtDetailsScreenEvents.Error, ArtDetailsScreenState> =
    ArtDetailsScreenEvents.Error(error) to
        previousState.copy(isLoading = false, error = error)
