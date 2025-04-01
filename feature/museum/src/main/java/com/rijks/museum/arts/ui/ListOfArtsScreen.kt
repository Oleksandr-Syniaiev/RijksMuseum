package com.rijks.museum.arts.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rijks.museum.arts.R
import com.rijks.museum.arts.ui.ListOfArtsScreenEvents.LoadMore
import com.rijks.museum.arts.ui.components.AuthorHeader
import com.rijks.museum.arts.ui.components.BaseToolbar
import com.rijks.museum.arts.ui.components.ItemContent
import com.rijks.museum.arts.ui.components.Loading
import com.rijks.museum.arts.ui.components.LoadingError
import com.rijks.museum.arts.ui.components.PagingErrorItem
import com.rijks.museum.arts.ui.components.PagingLoadingIndicator
import com.rijks.museum.arts.ui.state.ErrorState
import com.rijks.museum.arts.ui.state.ListOfArtsScreenState
import com.rijks.museum.core.ui.theme.RijksMuseumAppTheme
import com.rijks.museum.core.ui.utils.EMPTY_STRING
import com.rijks.museum.core.ui.utils.extensions.orFalse
import com.rijks.museum.core.ui.utils.states.ComposeMap
import com.rijks.museum.core.ui.utils.tools.rememberSaveableLazyListState
import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.domain.model.UiArtsObject

private const val PAGINATION_START_OFFSET = 3

@Composable
fun ListOfArtsScreen(viewModel: ListOfArtsViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LisOfArtsScreenContent(
        state = state,
        onEvent = remember { { event -> viewModel.onEvent(event) } },
    )
}

@Composable
private fun LisOfArtsScreenContent(
    state: ListOfArtsScreenState,
    onEvent: (ListOfArtsScreenEvents) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(topBar = {
        BaseToolbar(stringResource(R.string.toolbar_list_title))
    }) { contentPadding ->
        if (state.loadingState.isFullLoading) {
            Loading()
        } else {
            if (state.error != null && !state.error.isPagingError) {
                LoadingError(
                    error = state.error.dataError,
                    onClick = { onEvent(ListOfArtsScreenEvents.Loading) },
                )
            } else {
                GroupedListContent(
                    state = state,
                    onEvent = onEvent,
                    modifier = modifier,
                    contentPadding = contentPadding,
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun GroupedListContent(
    state: ListOfArtsScreenState,
    onEvent: (ListOfArtsScreenEvents) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val lazyListState = rememberSaveableLazyListState()
        val scrolledToEnd by remember {
            derivedStateOf {
                val lastVisibleItemIndex = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                val totalItemsCount = lazyListState.layoutInfo.totalItemsCount

                val value = (
                    lastVisibleItemIndex >= totalItemsCount - PAGINATION_START_OFFSET &&
                        totalItemsCount > 0
                )
                value
            }
        }
        LaunchedEffect(scrolledToEnd) {
            val isNotLoading =
                !state.loadingState.isLoadingMore &&
                    !state.loadingState.isFullLoading
            if (
                scrolledToEnd &&
                isNotLoading &&
                !state.pagingState.endReached
            ) {
                onEvent(LoadMore)
            }
        }

        LazyColumn(
            state = lazyListState,
            modifier = modifier,
            contentPadding = contentPadding,
        ) {
            state.content.items.forEach { (author, listOfItems) ->
                stickyHeader(key = author) {
                    AuthorHeader(author)
                }
                items(listOfItems, key = { it.id }) { listItem ->
                    ItemContent(
                        listItem,
                        onItemClick = { id ->
                            onEvent(ListOfArtsScreenEvents.ItemClicked(id))
                        },
                    )
                }
            }

            if (state.loadingState.isLoadingMore) {
                item {
                    PagingLoadingIndicator()
                }
            }
            if (state.error?.isPagingError.orFalse()) {
                item {
                    PagingErrorItem(
                        onClick = {
                            onEvent(LoadMore)
                        },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LisOfArtsScreenContentPreview() {
    RijksMuseumAppTheme {
        LisOfArtsScreenContent(
            state =
                ListOfArtsScreenState(
                    content =
                        ComposeMap(
                            mapOf(
                                "Rembrandt" to
                                    listOf(
                                        UiArtsObject(
                                            id = "1",
                                            title = "The Night Watch",
                                            image = EMPTY_STRING,
                                            author = "Rembrandt",
                                            objectNumber = EMPTY_STRING,
                                        ),
                                        UiArtsObject(
                                            id = "2",
                                            title = "The Night Watch 2",
                                            image = EMPTY_STRING,
                                            author = "Rembrandt",
                                            objectNumber = EMPTY_STRING,
                                        ),
                                    ),
                                "Van Gogh" to
                                    listOf(
                                        UiArtsObject(
                                            id = "3",
                                            title = "Starry Night",
                                            image = EMPTY_STRING,
                                            author = "Van Gogh",
                                            objectNumber = EMPTY_STRING,
                                        ),
                                    ),
                            ),
                        ),
                    error = ErrorState(dataError = DataError.Network.NO_INTERNET, isPagingError = true),
                ),
            onEvent = {},
        )
    }
}
