package com.rijks.museum.arts.ui.reducers

import com.rijks.museum.arts.ui.details.ArtDetailsScreenEvents
import com.rijks.museum.arts.ui.state.ArtDetailsScreenState
import com.rijks.museum.core.utils.reducer.Reducer
import javax.inject.Inject

class ArtDetailsReducer
    @Inject
    constructor() : Reducer<ArtDetailsScreenEvents, ArtDetailsScreenState> {
        override fun invoke(event: ArtDetailsScreenEvents): ArtDetailsScreenState.() -> ArtDetailsScreenState =
            {
                when (event) {
                    is ArtDetailsScreenEvents.Loading ->
                        copy(
                            isLoading = true,
                            error = null,
                        )

                    is ArtDetailsScreenEvents.Loaded ->
                        copy(
                            isLoading = false,
                            error = null,
                            content = event.model,
                        )

                    is ArtDetailsScreenEvents.Error -> copy(isLoading = false, error = event.error)
                    is ArtDetailsScreenEvents.HideError -> copy(error = null)
                    is ArtDetailsScreenEvents.Back -> this
                }
            }
    }
