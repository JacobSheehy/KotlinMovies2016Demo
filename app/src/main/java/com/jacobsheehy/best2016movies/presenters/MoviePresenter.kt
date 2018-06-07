package com.jacobsheehy.best2016movies.presenters

import android.content.Context
import android.net.ConnectivityManager
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
import android.support.v7.widget.SimpleItemAnimator



/**
 * Presenter class for the movies list. Hold the dataset (model list) and
 * reference to UI elements like recyclerview for view updates.
 *
 */
class MoviePresenter : MovieAdapter.Listener {

    // Primary data store
    var allMovies : ArrayList<Movie> = ArrayList()
    // UI and related adapters
    var recyclerMovies: RecyclerView? = null
    var loadingMoviesProgressBar: ProgressBar? = null
    private var adapter: MovieAdapter? = null
    // Application context
    var appContext: Context? = null

    // API constants
    val apiPage = 1
    val apiYear = 2016

    // Callback for HTTP calls through retrofit2. Display a toast
    // on error, and update the RecyclerView on success.
    private val responseCallback = object : Callback<MoviesResponse> {
        override fun onFailure(call: Call<MoviesResponse>?, t: Throwable?) {
            handleError()
        }

        override fun onResponse(call: Call<MoviesResponse>?, response: Response<MoviesResponse>?) {
            val movieResponse = response?.body()
            if(movieResponse!=null) {
                allMovies.clear()
                allMovies.addAll(movieResponse.movies)
                recyclerMovies?.adapter?.notifyDataSetChanged()
                loadingMoviesProgressBar?.visibility = View.GONE
            } else {
                handleError()
            }
        }
    }

    fun handleError() {
        loadingMoviesProgressBar?.visibility = View.GONE
    }

    // Use Retrofit and GSON to make API calls on the background
    // and to show data on the UI thread.
    fun requestMovies(recyclerView: RecyclerView, loadingMovies: ProgressBar) {
        recyclerMovies = recyclerView
        loadingMoviesProgressBar = loadingMovies
        loadingMoviesProgressBar?.visibility = View.VISIBLE

        // prevents item flicker on expand operation
        (recyclerMovies?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

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
                apiPage,
                apiYear)
        println("call ${call.request().url()}")
        call.enqueue(responseCallback)
    }

    // Handle click events on the show/hide overview button, update the UI on changes
    override fun onItemClicked(movie: Movie, position: Int, viewHolder: MovieAdapter.MovieViewHolder) {
        when(allMovies[position].isExpanded) {
            true ->  { allMovies[position].isExpanded = false }
            false -> { allMovies[position].isExpanded = true }
            null ->  { allMovies[position].isExpanded = true }
        }
        recyclerMovies?.adapter?.notifyItemChanged(position)
    }


    companion object {
        // Check the network connection status immediately, without
        // waiting for a callback on the main listener
        fun checkConnection(context: Context): Boolean {
            val connectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectionManager.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
    }

}