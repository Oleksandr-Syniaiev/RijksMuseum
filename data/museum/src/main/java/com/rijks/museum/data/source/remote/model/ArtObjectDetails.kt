package com.rijks.museum.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class ArtObjectDetails(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("longTitle")
    val longTitle: String?,
    @SerializedName("subTitle")
    val subTitle: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("objectNumber")
    val objectNumber: String,
    @SerializedName("principalMaker")
    val principalMaker: String,
    @SerializedName("webImage")
    val webImage: WebImage?,
)
