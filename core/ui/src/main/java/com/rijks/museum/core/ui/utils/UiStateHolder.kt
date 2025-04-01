package com.rijks.museum.core.ui.utils

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface UiStateHolder<UiState> {
    val uiState: StateFlow<UiState>

    fun updateState(reduce: UiState.() -> UiState)
}

/**
 * Default implementation of UiStateHolder that uses MutableStateFlow to hold UI state
 * This implementation is suitable for ViewModels that need to survive configuration changes
 */
class DefaultUiStateHolder<UiState>(init: UiState) : UiStateHolder<UiState> {
    private val _uiState = MutableStateFlow(init)
    override val uiState = _uiState.asStateFlow()

    override fun updateState(reduce: UiState.() -> UiState) {
        _uiState.update(reduce)
    }
}

/**
 * SavedStateHolder implementation that uses SavedStateHandle to retain state during configuration changes
 * Use this implementation when you need to restore state after process death
 */
class SavedStateHolder<UiState>(
    private val savedStateHandle: SavedStateHandle,
    private val key: String,
    init: UiState
) : UiStateHolder<UiState> {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        savedStateHandle.get<UiState>(key) ?: init
    )
    override val uiState = _uiState.asStateFlow()

    override fun updateState(reduce: UiState.() -> UiState) {
        _uiState.update { currentState ->
            val newState = reduce(currentState)
            savedStateHandle[key] = newState
            newState
        }
    }
}
