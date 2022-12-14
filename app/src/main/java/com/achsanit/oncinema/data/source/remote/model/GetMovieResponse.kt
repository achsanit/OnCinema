package com.achsanit.oncinema.data.source.remote.model


import com.google.gson.annotations.SerializedName

data class GetMovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)