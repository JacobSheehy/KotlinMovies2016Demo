package com.jacobsheehy.best2016movies.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Movie(
        @SerializedName("id") val filmId: Int,
        @SerializedName("title") val filmTitle: String,
        @SerializedName("popularity") val popularity: Double,
        @SerializedName("poster_path") val posterPath: String,
        @SerializedName("release_date") val releaseDate: Date,
        @SerializedName("overview") val overview: String,
        var isExpanded: Boolean? = false)
