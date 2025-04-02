package com.rijks.museum.arts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.Error
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.HideError
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.ItemClicked
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.LoadMore
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.Loaded
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.LoadedMore
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.Loading
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.PagingError
import com.rijks.museum.arts.ui.reducers.ListOfArtsReducer
import com.rijks.museum.arts.ui.state.ListOfArtsScreenState
import com.rijks.museum.core.ui.navigation.Navigator
import com.rijks.museum.core.ui.navigation.Route
import com.rijks.museum.core.ui.utils.DefaultUiStateHolder
import com.rijks.museum.core.ui.utils.UiStateHolder
import com.rijks.museum.core.ui.utils.states.ComposeMap
import com.rijks.museum.core.ui.utils.states.emptyComposeMap
import com.rijks.museum.core.utils.errors.fold
import com.rijks.museum.core.utils.errors.map
import com.rijks.museum.domain.usecase.GetGroupedListOfArtsUseCase
import com.rijks.museum.domain.usecase.MergePagingDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOfArtsViewModel
    @Inject
    constructor(
        val getListOfArtsUseCase: GetGroupedListOfArtsUseCase,
        private val mergePagingDataUseCase: MergePagingDataUseCase,
        val reducer: ListOfArtsReducer,
        private val navigator: Navigator,
    ) : ViewModel(),
        UiStateHolder<ListOfArtsScreenState> by DefaultUiStateHolder(
            ListOfArtsScreenState(content = emptyComposeMap()),
        ) {
        companion object {
            private const val PAGE_SIZE = 20
        }

        init {
            /**
             * Trigger the loading event to fetch the initial data in case if state is lost
             * for e.g. in case activity was destroyed and recreated.
             * This was done instead of using savedStateHandle to restore the state because
             * the size of the data could be large and we want to avoid TransactionTooLargeException
             * due to Bundle size limit.
             */
            if (uiState.value.content.items.isEmpty()) {
                onEvent(Loading)
            }
        }

        fun onEvent(event: ListOfArtsScreenEvents) {
            val reduced = reducer(event)
            updateState(reduced)
            when (event) {
                is Loading -> {
                    viewModelScope.launch {
                        // Starting from the page 1 because of API
                        getListOfArtsUseCase(page = 1, pageSize = PAGE_SIZE)
                            .fold(
                                onSuccess = { model ->
                                    onEvent(Loaded(ComposeMap(model)))
                                },
                                onError = { error -> onEvent(Error(error)) },
                            )
                    }
                }

                is LoadMore -> {
                    val currentState = uiState.value
                    if (!currentState.pagingState.endReached) {
                        val nextPage = currentState.pagingState.page + 1
                        viewModelScope.launch {
                            getListOfArtsUseCase(page = nextPage, pageSize = PAGE_SIZE)
                                .map { model ->
                                    val isEndReached = model.isEmpty() || model.values.all { it.isEmpty() }
                                    val mergedData =
                                        mergePagingDataUseCase.invoke(
                                            currentState.content.items.toMutableMap(),
                                            model,
                                        )
                                    PagingData(mergedData, isEndReached)
                                }
                                .fold(
                                    onSuccess = { data ->
                                        onEvent(
                                            LoadedMore(
                                                content = ComposeMap(data.data),
                                                page = nextPage,
                                                isEndReached = data.isEndReached,
                                            ),
                                        )
                                    },
                                    onError = { error -> onEvent(PagingError(error)) },
                                )
                        }
                    }
                }

                is ItemClicked -> {
                    viewModelScope.launch {
                        navigator.navigate(Route.ArtsDetailsScreen(event.id))
                    }
                }
                // Handled by state reducer
                is Error, is HideError, is Loaded, is LoadedMore, is PagingError -> Unit
            }
        }
    }
