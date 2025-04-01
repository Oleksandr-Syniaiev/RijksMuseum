package com.rijks.museum.domain.usecase

import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.core.utils.errors.SealedResult
import com.rijks.museum.domain.model.UiArtDetails
import com.rijks.museum.domain.repository.MuseumRepository
import javax.inject.Inject

class GetArtDetailsUseCase
    @Inject
    constructor(
        private val repository: MuseumRepository,
    ) {
        suspend operator fun invoke(objectNumber: String): SealedResult<UiArtDetails, DataError> = repository.getArtDetails(objectNumber)
    }
