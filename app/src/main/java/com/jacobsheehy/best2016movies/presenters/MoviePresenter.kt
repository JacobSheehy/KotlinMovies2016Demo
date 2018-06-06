package com.jacobsheehy.best2016movies.presenters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
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
    var loadingMoviesProgressBar: ProgressBar? = null
    var appContext: Context? = null
    private var adapter: MovieAdapter? = null


    private val responseCallback = object : Callback<MoviesResponse> {
        override fun onFailure(call: Call<MoviesResponse>?, t: Throwable?) {
            showError()
        }

        override fun onResponse(call: Call<MoviesResponse>?, response: Response<MoviesResponse>?) {
            val movieResponse = response?.body()
            if(movieResponse!=null) {
                allMovies.clear()
                allMovies.addAll(movieResponse.movies)
                recyclerMovies?.adapter?.notifyDataSetChanged()
                loadingMoviesProgressBar?.visibility = View.GONE
            } else {
                showError()
            }
        }
    }

    fun showError() {
        Toast.makeText(appContext, appContext?.getString(R.string.load_data_failure),Toast.LENGTH_LONG).show()
        loadingMoviesProgressBar?.visibility = View.GONE
    }

    fun requestMovies(recyclerView: RecyclerView, loadingMovies: ProgressBar) {
        recyclerMovies = recyclerView
        loadingMoviesProgressBar = loadingMovies
        loadingMoviesProgressBar?.visibility = View.VISIBLE

        adapter?.moviePresenter = this

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
        println("onitemclicked ${movie.filmTitle} ${movie.isExpanded}" )
        when(allMovies[position].isExpanded) {
            true ->  { allMovies[position].isExpanded = false }
            false -> { allMovies[position].isExpanded = true }
            null ->  { allMovies[position].isExpanded = true }
        }
        recyclerMovies?.adapter?.notifyItemChanged(position)
    }
}