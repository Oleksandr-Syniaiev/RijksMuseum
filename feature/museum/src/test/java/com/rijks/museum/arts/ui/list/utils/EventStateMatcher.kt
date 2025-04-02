package com.rijks.museum.arts.ui.list.utils

import com.rijks.museum.arts.ui.list.ListOfArtsScreenEvents
import com.rijks.museum.arts.ui.state.ErrorState
import com.rijks.museum.arts.ui.state.ListOfArtsScreenState
import com.rijks.museum.arts.ui.state.LoadingState
import com.rijks.museum.arts.ui.state.PagingState
import com.rijks.museum.core.ui.utils.states.ComposeMap
import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.domain.model.UiArtsObject

fun loadedEventToState(
    previousState: ListOfArtsScreenState,
    content: ComposeMap<String, List<UiArtsObject>>,
): Pair<ListOfArtsScreenEvents.Loaded, ListOfArtsScreenState> =
    ListOfArtsScreenEvents.Loaded(content) to
        previousState.copy(
            content = content,
            loadingState = LoadingState(isFullLoading = false, isLoadingMore = false),
        )

fun errorEventToState(
    previousState: ListOfArtsScreenState,
    error: DataError,
): Pair<ListOfArtsScreenEvents.Error, ListOfArtsScreenState> =
    ListOfArtsScreenEvents.Error(error) to
        previousState.copy(
            loadingState = LoadingState(isFullLoading = false, isLoadingMore = false),
            error = ErrorState(dataError = error, isPagingError = false),
        )

fun loadMoreEventToState(previousState: ListOfArtsScreenState): Pair<ListOfArtsScreenEvents.LoadMore, ListOfArtsScreenState> =
    ListOfArtsScreenEvents.LoadMore to
        previousState.copy(
            loadingState = LoadingState(isFullLoading = false, isLoadingMore = true),
            error = null,
        )

fun loadedMoreEventToState(
    previousState: ListOfArtsScreenState,
    content: ComposeMap<String, List<UiArtsObject>>,
    page: Int,
    isEndReached: Boolean,
): Pair<ListOfArtsScreenEvents.LoadedMore, ListOfArtsScreenState> =
    ListOfArtsScreenEvents.LoadedMore(
        content = content,
        page = page,
        isEndReached = isEndReached,
    ) to
        previousState.copy(
            content = content,
            loadingState = LoadingState(isFullLoading = false, isLoadingMore = false),
            pagingState =
                PagingState(
                    page = page,
                    endReached = isEndReached,
                ),
            error = null,
        )

fun pagingErrorEventToState(
    previousState: ListOfArtsScreenState,
    error: DataError,
): Pair<ListOfArtsScreenEvents.PagingError, ListOfArtsScreenState> =
    ListOfArtsScreenEvents.PagingError(error) to
        previousState.copy(
            loadingState = LoadingState(isFullLoading = false, isLoadingMore = false),
            error = ErrorState(dataError = error, isPagingError = true),
        )

fun itemClickEventToState(
    id: String,
    previousState: ListOfArtsScreenState,
): Pair<ListOfArtsScreenEvents.ItemClicked, ListOfArtsScreenState> = ListOfArtsScreenEvents.ItemClicked(id) to previousState
