package com.erel.movies.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val poster: String,
    val overview: String,
    val releaseDate: String,
    val id: Int,
    val originalTitle: String,
    val title: String,
    val voteAverage: String
) : Parcelable