package com.jacobsheehy.best2016movies.models

import com.google.gson.GsonBuilder
import com.google.gson.Gson
import android.provider.Settings.System.DATE_FORMAT



class MoviesResponse(var movies: List<Movie>? = null) {

    val DATE_FORMAT = "yyyy-MM-dd"


    companion object {
        fun parseJSON(response: String) : MoviesResponse {
            val gsonBuilder = GsonBuilder()
            gsonBuilder.setDateFormat(DATE_FORMAT)
            val gson = gsonBuilder.create()
            return gson.fromJson(response, MoviesResponse::class.java)
        }
    }

}