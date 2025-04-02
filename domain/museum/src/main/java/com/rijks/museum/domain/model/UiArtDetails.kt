package com.rijks.museum.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiArtDetails(
    val id: String = "",
    val title: String = "",
    val longTitle: String = "",
    val subTitle: String = "",
    val image: String = "",
    val author: String = "",
    val description: String = "",
) : Parcelable
