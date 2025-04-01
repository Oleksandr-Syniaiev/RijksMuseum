package com.rijks.museum.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class ArtObjectDetailsResponse(
    @SerializedName("artObject")
    val artObject: ArtObjectDetails,
)
