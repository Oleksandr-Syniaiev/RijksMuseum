package com.rijks.museum.data.repository

import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.core.utils.errors.SealedResult
import com.rijks.museum.data.source.remote.RemoteMuseumDataSource
import com.rijks.museum.domain.model.UiArtDetails
import com.rijks.museum.domain.model.UiArtsObject
import com.rijks.museum.domain.repository.MuseumRepository
import javax.inject.Inject

class RemoteMuseumRepository @Inject constructor(
    private val remoteDataSource: RemoteMuseumDataSource,
) : MuseumRepository {
    /**
     * The language used for the API requests.
     * Currently set to English (en).
     */
    companion object {
        const val LANGUAGE = "en"
    }

    override suspend fun getListOfArts(
        pageSize: Int,
        page: Int,
    ): SealedResult<List<UiArtsObject>, DataError> =
        remoteDataSource.getListOfArts(
            pageSize = pageSize,
            page = page,
            lang = LANGUAGE
        )

    override suspend fun getArtDetails(objectNumber: String): SealedResult<UiArtDetails, DataError> =
        remoteDataSource.getArtDetails(
            artId = objectNumber,
            lang = LANGUAGE
        )
}
