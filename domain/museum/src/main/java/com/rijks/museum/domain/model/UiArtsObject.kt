package com.rijks.museum.domain.model

import android.os.Parcel
import android.os.Parcelable

data class UiArtsObject(
    val id: String,
    val title: String,
    val image: String,
    val author: String,
    val objectNumber: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
    )

    override fun writeToParcel(
        parcel: Parcel,
        flags: Int,
    ) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(image)
        parcel.writeString(author)
        parcel.writeString(objectNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UiArtsObject> {
        override fun createFromParcel(parcel: Parcel): UiArtsObject {
            return UiArtsObject(parcel)
        }

        override fun newArray(size: Int): Array<UiArtsObject?> {
            return arrayOfNulls(size)
        }
    }
}
