package com.rijks.museum.domain.repository

import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.core.utils.errors.SealedResult
import com.rijks.museum.domain.model.UiArtsObject

interface MuseumRepository {

    suspend fun get(): SealedResult<List<UiArtsObject>, DataError>
}
