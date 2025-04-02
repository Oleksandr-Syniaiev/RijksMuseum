package com.rijks.museum.arts.ui.list.utils

import com.rijks.museum.domain.model.UiArtsObject
import com.rijks.museum.domain.usecase.MergePagingDataUseCase
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stubbing

class MergePagingDataUseCaseFixture {
    val useCase: MergePagingDataUseCase = mock()

    fun givenPaging(
        oldData: MutableMap<String, List<UiArtsObject>>,
        newData: Map<String, List<UiArtsObject>>,
        result: Map<String, List<UiArtsObject>>,
    ) {
        stubbing(useCase) {
            onBlocking { invoke(oldData, newData) } doReturn result
        }
    }
}
