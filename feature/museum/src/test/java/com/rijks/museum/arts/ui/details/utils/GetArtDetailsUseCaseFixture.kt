package com.rijks.museum.arts.ui.details.utils

import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.core.utils.errors.SealedResult
import com.rijks.museum.domain.model.UiArtDetails
import com.rijks.museum.domain.usecase.GetArtDetailsUseCase
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stubbing

class GetArtDetailsUseCaseFixture {
    val useCase: GetArtDetailsUseCase = mock()

    fun givenArtDetails(
        objectNumber: String,
        result: SealedResult<UiArtDetails, DataError>,
    ) {
        stubbing(useCase) {
            onBlocking { invoke(objectNumber) } doReturn result
        }
    }
}
