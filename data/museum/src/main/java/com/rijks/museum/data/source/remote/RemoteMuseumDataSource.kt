package com.rijks.museum.data.source.remote

import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.core.utils.errors.SealedResult
import com.rijks.museum.domain.model.UiArtDetails
import com.rijks.museum.domain.model.UiArtsObject

interface RemoteMuseumDataSource {
    suspend fun getListOfArts(
        page: Int,
        pageSize: Int,
        lang: String
    ): SealedResult<List<UiArtsObject>, DataError>

    suspend fun getArtDetails(
        artId: String,
        lang: String
    ): SealedResult<UiArtDetails, DataError>
}
