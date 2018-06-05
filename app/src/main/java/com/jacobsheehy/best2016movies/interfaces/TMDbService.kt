package com.jacobsheehy.best2016movies.interfaces

import android.graphics.Movie
import com.jacobsheehy.best2016movies.models.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import rx.Observable

interface TMDbService {
    @GET("discover/movie?api_key=1821c6b6049945b0e08619035590d15b&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&year=2016")
    fun listMovies(): Call<MoviesResponse>

}