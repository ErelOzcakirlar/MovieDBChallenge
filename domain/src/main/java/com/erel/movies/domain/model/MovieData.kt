package com.erel.movies.domain.model

import com.google.gson.annotations.SerializedName

data class MovieData(
    @SerializedName("poster_path") val poster: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("vote_average") val voteAverage: Float?
)