package com.rijks.museum.arts.ui.state

import androidx.compose.runtime.Stable
import com.rijks.museum.core.ui.utils.states.ComposeMap
import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.domain.model.UiArtsObject

@Stable
data class ListOfArtsScreenState(
    val content: ComposeMap<String, List<UiArtsObject>>,
    val loadingState: LoadingState = LoadingState(),
    val pagingState: PagingState = PagingState(),
    val error: ErrorState? = null,
)

@Stable
data class LoadingState(
    val isFullLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
)

@Stable
data class PagingState(
    val endReached: Boolean = false,
    val page: Int = 1,
    val totalSize: Int = 0
)

@Stable
data class ErrorState(
    val dataError: DataError,
    val isPagingError: Boolean = false,
)
