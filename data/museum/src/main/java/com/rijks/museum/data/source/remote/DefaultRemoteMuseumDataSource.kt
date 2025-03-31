package com.rijks.museum.data.source.remote

import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.core.utils.errors.SealedResult
import com.rijks.museum.core.utils.errors.wrapThrowable
import com.rijks.museum.data.mapper.ListOfArtsResponseToUiMapper
import com.rijks.museum.data.source.DefaultApiErrorMapper
import com.rijks.museum.domain.model.UiArtsObject
import javax.inject.Inject

class DefaultRemoteMuseumDataSource @Inject constructor(
    private val apiService: MuseumApiService,
    private val errorMapper: DefaultApiErrorMapper
) : RemoteMuseumDataSource {

    override suspend fun getListOfArts(): SealedResult<List<UiArtsObject>, DataError> = wrapThrowable(
        {
            apiService.getListOfArts(
                culture = "en",
                page = 0,
                pageSize = 2,

            ).let {
                ListOfArtsResponseToUiMapper.mapFrom(it)
            }
        },
        { errorMapper.map(it) }
    )
}
