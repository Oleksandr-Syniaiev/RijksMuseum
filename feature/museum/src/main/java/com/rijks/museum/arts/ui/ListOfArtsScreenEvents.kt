package com.rijks.museum.arts.ui

import androidx.compose.runtime.Stable
import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.domain.model.UiArtsObject

@Stable
sealed class ListOfArtsScreenEvents {

    data object Loading : ListOfArtsScreenEvents()

    data class Loaded(val model: List<UiArtsObject>) : ListOfArtsScreenEvents()

    data class Error(val error: DataError) : ListOfArtsScreenEvents()

    data object HideError : ListOfArtsScreenEvents()

}
