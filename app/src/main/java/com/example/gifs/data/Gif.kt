package com.example.gifs.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Gif(
    val id: String,
    val images: Map<String, Image>,
    val title: String?,
    val username: String?
) : Parcelable

data class DataResponse<T>(
    val data: T
)

@Parcelize
data class Image(
    val url: String,
    val width: Int,
    val height: Int,
    val size: Int
) : Parcelable
