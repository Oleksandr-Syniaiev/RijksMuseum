package com.rijks.museum.arts.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rijks.museum.arts.ui.reducers.ArtDetailsReducer
import com.rijks.museum.arts.ui.state.ArtDetailsScreenState
import com.rijks.museum.core.ui.navigation.Navigator
import com.rijks.museum.core.ui.utils.DefaultUiStateHolder
import com.rijks.museum.core.ui.utils.UiStateHolder
import com.rijks.museum.core.utils.errors.fold
import com.rijks.museum.domain.model.UiArtDetails
import com.rijks.museum.domain.usecase.GetArtDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtDetailsViewModel
    @Inject
    constructor(
        val getArtDetailsUseCase: GetArtDetailsUseCase,
        val reducer: ArtDetailsReducer,
        private val navigator: Navigator,
        private val savedStateHandle: SavedStateHandle,
    ) : ViewModel(),
        UiStateHolder<ArtDetailsScreenState> by DefaultUiStateHolder(
            ArtDetailsScreenState(content = UiArtDetails()),
        ) {
        companion object {
            const val ART_ID_KEY = "art_id"
            const val HAS_LOADED_KEY = "has_loaded"
            const val ART_CONTENT_KEY = "art_content"
        }

        init {
            restoreState()
        }

        fun onEvent(event: ArtDetailsScreenEvents) {
            val reduced = reducer(event)
            when (event) {
                is ArtDetailsScreenEvents.Loading -> {
                    savedStateHandle[ART_ID_KEY] = event.objectNumber
                    if (isNeedToLoad(event.objectNumber)) {
                        updateState(reduced)
                        viewModelScope.launch {
                            getArtDetailsUseCase(event.objectNumber).fold(onSuccess = { model ->
                                savedStateHandle[HAS_LOADED_KEY] = true
                                savedStateHandle[ART_CONTENT_KEY] = model
                                onEvent(ArtDetailsScreenEvents.Loaded(model))
                            }, onError = { error -> onEvent(ArtDetailsScreenEvents.Error(error)) })
                        }
                    }
                }

                is ArtDetailsScreenEvents.Loaded -> {
                    updateState(reduced)
                    savedStateHandle[ART_CONTENT_KEY] = event.model
                }

                is ArtDetailsScreenEvents.Back ->
                    viewModelScope.launch {
                        navigator.navigateUp()
                    }

                else -> {
                    updateState(reduced)
                }
            }
        }

        private fun restoreState() {
            val hasLoaded = savedStateHandle.get<Boolean>(HAS_LOADED_KEY) ?: false
            val savedArtId = savedStateHandle.get<String>(ART_ID_KEY)
            val savedContent = savedStateHandle.get<UiArtDetails>(ART_CONTENT_KEY)

            if (hasLoaded && savedArtId != null && savedContent != null) {
                updateState {
                    copy(
                        content = savedContent,
                        isLoading = false,
                        error = null,
                    )
                }
            } else if (savedArtId != null) {
                onEvent(ArtDetailsScreenEvents.Loading(savedArtId))
            }
        }

        private fun isNeedToLoad(objectNumber: String): Boolean {
            val hasLoaded = savedStateHandle.get<Boolean>(HAS_LOADED_KEY) ?: false
            val savedArtId = savedStateHandle.get<String>(ART_ID_KEY)
            val savedContent = savedStateHandle.get<UiArtDetails>(ART_CONTENT_KEY)
            return !hasLoaded || savedArtId != objectNumber || savedContent == null
        }
    }
