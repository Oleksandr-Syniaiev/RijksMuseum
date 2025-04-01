package com.rijks.museum.arts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.Error
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.ItemClicked
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.LoadMore
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.Loaded
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
import com.rijks.museum.domain.model.UiArtsObject
import com.rijks.museum.domain.usecase.GetGroupedListOfArtsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOfArtsViewModel
    @Inject
    constructor(
        val getListOfArtsUseCase: GetGroupedListOfArtsUseCase,
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
                                .fold(
                                    onSuccess = { model ->
                                        val isEndReached = model.isEmpty() || model.values.all { it.isEmpty() }
                                        val mergedData = mergeData(currentState.content.items.toMutableMap(), model)
                                        onEvent(
                                            ListOfArtsScreenEvents.LoadedMore(
                                                content = ComposeMap(mergedData),
                                                page = nextPage,
                                                isEndReached = isEndReached,
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

                else -> Unit
            }
        }

        private fun mergeData(
            mergedData: MutableMap<String, List<UiArtsObject>>,
            model: Map<String, List<UiArtsObject>>,
        ): MutableMap<String, List<UiArtsObject>> {
            model.forEach { (author, items) ->
                val existingItems = mergedData[author] ?: emptyList()
                val existingIds = existingItems.map { it.id }.toSet()
                val newUniqueItems = items.filterNot { it.id in existingIds }
                if (newUniqueItems.isNotEmpty()) {
                    mergedData[author] = existingItems + newUniqueItems
                }
            }

            model.keys.forEach { author ->
                if (!mergedData.containsKey(author)) {
                    mergedData[author] = model[author] ?: emptyList()
                }
            }
            return mergedData
        }
    }
