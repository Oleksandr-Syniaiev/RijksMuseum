package com.rijks.museum.data.source.remote

import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.core.utils.errors.SealedResult
import com.rijks.museum.domain.model.UiArtsObject

interface RemoteMuseumDataSource {
    suspend fun getListOfArts(): SealedResult<List<UiArtsObject>, DataError>
}
