package com.rijks.museum.arts.ui

import androidx.compose.runtime.Stable
import com.rijks.museum.core.ui.utils.states.ComposeMap
import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.domain.model.UiArtsObject

@Stable
sealed class ListOfArtsScreenEvents {

    data object Loading : ListOfArtsScreenEvents()

    data class Loaded(val model: ComposeMap<String, List<UiArtsObject>>) : ListOfArtsScreenEvents()

    data class LoadedMore(
        val content: ComposeMap<String, List<UiArtsObject>>,
        val page: Int,
        val isEndReached: Boolean
    ) : ListOfArtsScreenEvents()

    data class Error(val error: DataError) : ListOfArtsScreenEvents()

    data class PagingError(val error: DataError) : ListOfArtsScreenEvents()

    data class ItemClicked(val id: String) : ListOfArtsScreenEvents()

    data object LoadMore : ListOfArtsScreenEvents()

    data object HideError : ListOfArtsScreenEvents()

}
