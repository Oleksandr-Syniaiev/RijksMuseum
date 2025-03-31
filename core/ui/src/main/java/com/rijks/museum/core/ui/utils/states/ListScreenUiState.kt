package com.rijks.museum.core.ui.utils.states

data class ListScreenUiState<T>(
    val items: ComposeList<T> = ComposeList(emptyList()),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
