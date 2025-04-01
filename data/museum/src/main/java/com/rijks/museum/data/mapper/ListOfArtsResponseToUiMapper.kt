package com.rijks.museum.data.mapper

import com.rijks.museum.core.utils.Mapper
import com.rijks.museum.data.source.remote.model.ListOfArtsResponse
import com.rijks.museum.domain.model.UiArtsObject

object ListOfArtsResponseToUiMapper : Mapper<ListOfArtsResponse, List<UiArtsObject>> {
    override fun mapFrom(from: ListOfArtsResponse): List<UiArtsObject> =
        from.artObjects.map {
            UiArtsObject(
                id = it.id,
                title = it.title,
                image = it.webImage?.url.orEmpty(),
                author = it.principalOrFirstMaker,
                objectNumber = it.objectNumber,
            )
        }
}
