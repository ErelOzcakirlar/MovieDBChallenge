package com.erel.movies.domain.model

import com.google.gson.annotations.SerializedName

data class Response<T>(
    @SerializedName("results") val results: T?,
    @SerializedName("page") val page: Int?,
    @SerializedName("total_pages") val totalPages: Int?
)
