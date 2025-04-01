package com.rijks.museum.domain.model

import android.os.Parcel
import android.os.Parcelable

data class UiArtDetails(
    val id: String = "",
    val title: String = "",
    val longTitle: String = "",
    val subTitle: String = "",
    val image: String = "",
    val author: String = "",
    val description: String = "",
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(longTitle)
        parcel.writeString(subTitle)
        parcel.writeString(image)
        parcel.writeString(author)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UiArtDetails> {
        override fun createFromParcel(parcel: Parcel): UiArtDetails {
            return UiArtDetails(parcel)
        }

        override fun newArray(size: Int): Array<UiArtDetails?> {
            return arrayOfNulls(size)
        }
    }
}
