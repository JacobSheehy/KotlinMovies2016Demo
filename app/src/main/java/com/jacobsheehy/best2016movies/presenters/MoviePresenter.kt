package com.jacobsheehy.best2016movies.presenters

import com.jacobsheehy.best2016movies.interfaces.TMDbService
import com.jacobsheehy.best2016movies.models.MoviesResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory


class MoviePresenter {

    val responseCallback = object : Callback<MoviesResponse> {
        override fun onFailure(call: Call<MoviesResponse>?, t: Throwable?) {
            println("failure on api call") // TODO handle this
        }

        override fun onResponse(call: Call<MoviesResponse>?, response: Response<MoviesResponse>?) {
            val movieResponse = response?.body()
            if(movieResponse!=null) {
                println("found ${movieResponse.movies.size} movies") // TODO handle this

            } else {
                println("response body null") // TODO handle this
            }
        }
    }

    fun requestMovies() {
        val client = OkHttpClient()

        val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.themoviedb.org/3/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(TMDbService::class.java)

        val call = service.listMovies()
        call.enqueue(responseCallback)
    }

}