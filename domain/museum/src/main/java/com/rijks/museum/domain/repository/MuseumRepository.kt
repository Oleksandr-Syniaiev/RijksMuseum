package com.rijks.museum.domain.repository

import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.core.utils.errors.SealedResult
import com.rijks.museum.domain.model.UiArtDetails
import com.rijks.museum.domain.model.UiArtsObject

interface MuseumRepository {
    suspend fun getListOfArts(
        pageSize: Int,
        page: Int,
    ): SealedResult<List<UiArtsObject>, DataError>

    suspend fun getArtDetails(
        objectNumber: String,
    ): SealedResult<UiArtDetails, DataError>
}
