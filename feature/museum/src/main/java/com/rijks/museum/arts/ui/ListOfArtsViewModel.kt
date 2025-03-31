package com.rijks.museum.arts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.Error
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.Loaded
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.Loading
import com.rijks.museum.arts.ui.reducers.ListOfArtsReducer
import com.rijks.museum.arts.ui.state.ListOfArtsScreenState
import com.rijks.museum.core.ui.utils.DefaultUiStateHolder
import com.rijks.museum.core.ui.utils.UiStateHolder
import com.rijks.museum.core.ui.utils.states.emptyComposeList
import com.rijks.museum.core.utils.errors.fold
import com.rijks.museum.domain.usecase.GetListOfArtsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOfArtsViewModel @Inject constructor(
    val getListOfArtsUseCase: GetListOfArtsUseCase,
    val reducer: ListOfArtsReducer,
) : ViewModel(),
    UiStateHolder<ListOfArtsScreenState> by DefaultUiStateHolder(
        ListOfArtsScreenState(content = emptyComposeList())
    ) {

    init {
        onEvent(Loading)
    }

    fun onEvent(event: ListOfArtsScreenEvents) {
        val reduced = reducer(event)
        updateState(reduced)
        when (event) {
            is Loading -> {
                viewModelScope.launch {
                    getListOfArtsUseCase().fold(
                        onSuccess = { model -> onEvent(Loaded(model)) },
                        onError = { error -> onEvent(Error(error)) }
                    )
                }
            }

            else -> {}
        }
    }
}
