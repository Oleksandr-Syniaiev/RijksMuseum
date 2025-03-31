package com.rijks.museum.core.ui.utils.states

data class ItemUiState<T>(
    val item: T,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
