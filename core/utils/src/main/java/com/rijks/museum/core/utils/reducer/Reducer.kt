package com.rijks.museum.core.utils.reducer

interface Reducer<Event, UiState> {

   operator fun invoke(event: Event): UiState.() -> UiState
}
