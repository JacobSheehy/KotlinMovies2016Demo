package com.jacobsheehy.best2016movies.models

import com.google.gson.annotations.SerializedName

/**
 * Accept a list of movies with metadata as a JSON response
 */
class MoviesResponse(
        @SerializedName("results")
        val movies: List<Movie> )