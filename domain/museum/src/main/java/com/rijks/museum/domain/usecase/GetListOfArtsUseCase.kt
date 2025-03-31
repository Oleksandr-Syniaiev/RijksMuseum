package com.rijks.museum.domain.usecase

import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.core.utils.errors.SealedResult
import com.rijks.museum.domain.model.UiArtsObject
import com.rijks.museum.domain.repository.MuseumRepository
import javax.inject.Inject

class GetListOfArtsUseCase @Inject constructor(
    private val repository: MuseumRepository
) {
    suspend operator fun invoke(): SealedResult<List<UiArtsObject>, DataError> {
        return repository.get()
    }
}
