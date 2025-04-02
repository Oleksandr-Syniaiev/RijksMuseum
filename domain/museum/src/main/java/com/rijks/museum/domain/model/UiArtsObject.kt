package com.rijks.museum.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiArtsObject(
    val id: String,
    val title: String,
    val image: String,
    val author: String,
    val objectNumber: String,
) : Parcelable
