package com.rijks.museum.core.ui.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface UiStateHolder<UiState> {
    val uiState: StateFlow<UiState>

    fun updateState(reduce: UiState.() -> UiState)
}

class DefaultUiStateHolder<UiState>(init: UiState) : UiStateHolder<UiState> {
    private val _uiState = MutableStateFlow(init)
    override val uiState = _uiState.asStateFlow()

    override fun updateState(reduce: UiState.() -> UiState) {
        _uiState.update(reduce)
    }
}
