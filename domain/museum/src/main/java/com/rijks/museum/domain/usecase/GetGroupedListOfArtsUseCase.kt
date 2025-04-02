package com.rijks.museum.domain.usecase

import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.core.utils.errors.SealedResult
import com.rijks.museum.core.utils.errors.map
import com.rijks.museum.domain.model.UiArtsObject
import com.rijks.museum.domain.repository.MuseumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetGroupedListOfArtsUseCase
    @Inject
    constructor(
        private val repository: MuseumRepository,
    ) {
        suspend operator fun invoke(
            pageSize: Int,
            page: Int,
        ): SealedResult<Map<String, List<UiArtsObject>>, DataError> =
            withContext(Dispatchers.IO) {
                repository.getListOfArts(
                    pageSize = pageSize,
                    page = page,
                ).map { items ->
                    items.groupBy { it.author }
                }
            }
    }
