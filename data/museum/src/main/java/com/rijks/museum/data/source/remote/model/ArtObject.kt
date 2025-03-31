package com.rijks.museum.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class ArtObject(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("longTitle")
    val longTitle: String,
    @SerializedName("objectNumber")
    val objectNumber: String,
    @SerializedName("principalOrFirstMaker")
    val principalOrFirstMaker: String,
    @SerializedName("webImage")
    val webImage: WebImage,
)
