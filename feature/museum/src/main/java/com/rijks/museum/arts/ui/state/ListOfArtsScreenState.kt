package com.rijks.museum.arts.ui.state

import androidx.compose.runtime.Stable
import com.rijks.museum.core.ui.utils.states.ComposeList
import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.domain.model.UiArtsObject

@Stable
data class ListOfArtsScreenState(
    val content: ComposeList<UiArtsObject>,
    val loadingState: LoadingState = LoadingState(),
    val error: DataError? = null,
)

data class LoadingState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false
)
