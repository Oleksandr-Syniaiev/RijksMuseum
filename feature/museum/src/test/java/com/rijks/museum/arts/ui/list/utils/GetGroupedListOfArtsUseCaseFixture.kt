package com.rijks.museum.arts.ui.list.utils

import com.rijks.museum.core.utils.errors.DataError
import com.rijks.museum.core.utils.errors.SealedResult
import com.rijks.museum.domain.model.UiArtsObject
import com.rijks.museum.domain.usecase.GetGroupedListOfArtsUseCase
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stubbing

class GetGroupedListOfArtsUseCaseFixture {
    val useCase: GetGroupedListOfArtsUseCase = mock()

    fun givenListOfArts(
        pageSize: Int,
        page: Int,
        result: SealedResult<Map<String, List<UiArtsObject>>, DataError>,
    ) {
        stubbing(useCase) {
            onBlocking { invoke(pageSize, page) } doReturn result
        }
    }
}
