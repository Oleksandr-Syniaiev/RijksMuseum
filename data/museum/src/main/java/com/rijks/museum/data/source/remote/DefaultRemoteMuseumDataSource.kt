package com.rijks.museum.data.source.remote

import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.core.utils.errors.SealedResult
import com.rijks.museum.core.utils.errors.wrapThrowable
import com.rijks.museum.data.mapper.ArtDetailsResponseToUiMapper
import com.rijks.museum.data.mapper.ListOfArtsResponseToUiMapper
import com.rijks.museum.data.source.DefaultApiErrorMapper
import com.rijks.museum.domain.model.UiArtDetails
import com.rijks.museum.domain.model.UiArtsObject
import javax.inject.Inject

class DefaultRemoteMuseumDataSource
    @Inject
    constructor(
        private val apiService: MuseumApiService,
        private val errorMapper: DefaultApiErrorMapper,
    ) : RemoteMuseumDataSource {
        override suspend fun getListOfArts(
            page: Int,
            pageSize: Int,
            lang: String,
        ): SealedResult<List<UiArtsObject>, DataError> =
            wrapThrowable(
                {
                    apiService.getListOfArts(
                        culture = lang,
                        page = page,
                        pageSize = pageSize,
                    ).let {
                        ListOfArtsResponseToUiMapper.mapFrom(it)
                    }
                },
                { errorMapper.map(it) },
            )

        override suspend fun getArtDetails(
            artId: String,
            lang: String,
        ): SealedResult<UiArtDetails, DataError> =
            wrapThrowable(
                {
                    apiService.getArtDetails(
                        culture = lang,
                        objectNumber = artId,
                    ).let {
                        ArtDetailsResponseToUiMapper.mapFrom(it)
                    }
                },
                { errorMapper.map(it) },
            )
    }
