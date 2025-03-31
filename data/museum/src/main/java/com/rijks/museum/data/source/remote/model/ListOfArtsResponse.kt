package com.rijks.museum.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class ListOfArtsResponse(
    @SerializedName("artObjects")
    val artObjects: List<ArtObject>
)
