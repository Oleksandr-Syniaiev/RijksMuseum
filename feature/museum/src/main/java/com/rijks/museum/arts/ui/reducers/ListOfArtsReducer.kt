package com.rijks.museum.arts.ui.reducers

import com.rijks.museum.arts.ui.ListOfArtsScreenEvents
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.Error
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.HideError
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.ItemClicked
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.LoadMore
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.LoadedMore
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.Loading
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.PagingError
import com.rijks.museum.arts.ui.state.ErrorState
import com.rijks.museum.arts.ui.state.ListOfArtsScreenState
import com.rijks.museum.arts.ui.state.LoadingState
import com.rijks.museum.arts.ui.state.PagingState
import com.rijks.museum.core.utils.reducer.Reducer
import javax.inject.Inject

class ListOfArtsReducer
    @Inject
    constructor() : Reducer<ListOfArtsScreenEvents, ListOfArtsScreenState> {
        override fun invoke(event: ListOfArtsScreenEvents): ListOfArtsScreenState.() -> ListOfArtsScreenState =
            {
                when (event) {
                    is Loading ->
                        copy(
                            loadingState = LoadingState(isFullLoading = true, isLoadingMore = false),
                            error = null,
                        )

                    is ListOfArtsScreenEvents.Loaded ->
                        copy(
                            content = event.model,
                            loadingState = LoadingState(isFullLoading = false, isLoadingMore = false),
                            pagingState = PagingState(),
                            error = null,
                        )

                    is LoadMore ->
                        copy(
                            loadingState = LoadingState(isFullLoading = false, isLoadingMore = true),
                            error = null,
                        )

                    is LoadedMore ->
                        copy(
                            content = event.content,
                            loadingState = LoadingState(isFullLoading = false, isLoadingMore = false),
                            pagingState =
                                PagingState(
                                    page = event.page,
                                    endReached = event.isEndReached,
                                ),
                            error = null,
                        )

                    is Error ->
                        copy(
                            loadingState = LoadingState(isFullLoading = false, isLoadingMore = false),
                            error = ErrorState(dataError = event.error, isPagingError = false),
                        )

                    is PagingError ->
                        copy(
                            loadingState = LoadingState(isFullLoading = false, isLoadingMore = false),
                            error = ErrorState(dataError = event.error, isPagingError = true),
                        )

                    is HideError ->
                        copy(
                            error = null,
                        )

                    is ItemClicked -> this
                }
            }
    }
