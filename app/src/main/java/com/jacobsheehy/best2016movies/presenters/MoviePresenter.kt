package com.jacobsheehy.best2016movies.presenters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.jacobsheehy.best2016movies.InternalConfig
import com.jacobsheehy.best2016movies.R
import com.jacobsheehy.best2016movies.adapters.MovieAdapter
import com.jacobsheehy.best2016movies.interfaces.TMDbService
import com.jacobsheehy.best2016movies.models.Movie
import com.jacobsheehy.best2016movies.models.MoviesResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory

class MoviePresenter : MovieAdapter.Listener {

    var allMovies : ArrayList<Movie> = ArrayList()
    var recyclerMovies: RecyclerView? = null
    var appContext: Context? = null

    val responseCallback = object : Callback<MoviesResponse> {
        override fun onFailure(call: Call<MoviesResponse>?, t: Throwable?) {
            showErrorToast()
        }

        override fun onResponse(call: Call<MoviesResponse>?, response: Response<MoviesResponse>?) {
            val movieResponse = response?.body()
            if(movieResponse!=null) {
                allMovies.clear()
                allMovies.addAll(movieResponse.movies)
                recyclerMovies?.adapter?.notifyDataSetChanged()

            } else {
                showErrorToast()
            }
        }
    }

    fun showErrorToast() {
        Toast.makeText(appContext, appContext?.getString(R.string.load_data_failure),Toast.LENGTH_LONG).show()
    }

    fun requestMovies(recyclerView: RecyclerView, context: Context) {

        recyclerMovies = recyclerView

        val client = OkHttpClient()

        val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.themoviedb.org/3/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(TMDbService::class.java)

        val call = service.listMovies(InternalConfig.API_KEY,
                "en-US",
                "popularity.desc",
                false,
                false,
                1,
                2016)
        call.enqueue(responseCallback)
    }

    override fun onItemClicked(movie: Movie, position: Int, viewHolder: MovieAdapter.MovieViewHolder) {
        if(viewHolder.isExpanded) {
            viewHolder.hideOverview()
        } else {
            viewHolder.expandOverview()
        }
    }
}
