package com.rijks.museum.data.repository

import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.core.utils.errors.SealedResult
import com.rijks.museum.data.source.remote.RemoteMuseumDataSource
import com.rijks.museum.domain.model.UiArtsObject
import com.rijks.museum.domain.repository.MuseumRepository
import javax.inject.Inject

class RemoteMuseumRepository @Inject constructor(
    private val remoteDataSource: RemoteMuseumDataSource,
) : MuseumRepository {

    override suspend fun get(): SealedResult<List<UiArtsObject>, DataError> =
        remoteDataSource.getListOfArts()

}
