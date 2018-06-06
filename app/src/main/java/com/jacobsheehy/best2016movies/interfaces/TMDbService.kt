package com.jacobsheehy.best2016movies.interfaces

import com.jacobsheehy.best2016movies.models.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Define an interface for retrofit2 for
 * the API call endpoint of interest (discover/movie)
 */
interface TMDbService {

    @GET("discover/movie")
    fun listMovies(@Query("api_key") apiKey: String,
                   @Query("language") language: String,
                   @Query("sort_by") sortBy: String,
                   @Query("include_adult") includeAdult: Boolean,
                   @Query("include_video") includeVideo: Boolean,
                   @Query("page") page: Int,
                   @Query("year") year: Int): Call<MoviesResponse>

}