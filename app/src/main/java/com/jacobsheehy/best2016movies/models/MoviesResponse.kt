package com.jacobsheehy.best2016movies.models

import com.google.gson.GsonBuilder
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken


class MoviesResponse(
        @SerializedName("results")
        val movies: List<Movie> ) {

    companion object {
        val MOVIE_DATE_FORMAT = "yyyy-MM-dd"

        fun parseJSON(response: String) : MoviesResponse {
            val gsonBuilder = GsonBuilder()
            gsonBuilder.setDateFormat(MOVIE_DATE_FORMAT)
            val gson = gsonBuilder.create()
            return gson.fromJson(response, MoviesResponse::class.java)
        }
    }



}