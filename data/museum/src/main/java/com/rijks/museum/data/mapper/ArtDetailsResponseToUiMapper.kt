package com.rijks.museum.data.mapper

import com.rijks.museum.core.utils.Mapper
import com.rijks.museum.data.source.remote.model.ArtObjectDetailsResponse
import com.rijks.museum.domain.model.UiArtDetails

object ArtDetailsResponseToUiMapper : Mapper<ArtObjectDetailsResponse, UiArtDetails> {

    override fun mapFrom(from: ArtObjectDetailsResponse): UiArtDetails = UiArtDetails(
        id = from.artObject.id,
        title = from.artObject.title,
        image = from.artObject.webImage?.url.orEmpty(),
        author = from.artObject.principalMaker,
        description = from.artObject.description.orEmpty(),
        longTitle = from.artObject.longTitle.orEmpty(),
        subTitle = from.artObject.subTitle.orEmpty(),
    )
}
