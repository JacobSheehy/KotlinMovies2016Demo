package com.jacobsheehy.best2016movies.models

import com.google.gson.annotations.SerializedName



class MoviesResponse(
        @SerializedName("results")
        val movies: List<Movie> )