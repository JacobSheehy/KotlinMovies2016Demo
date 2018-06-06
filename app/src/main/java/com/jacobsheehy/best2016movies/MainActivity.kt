package com.jacobsheehy.best2016movies

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jacobsheehy.best2016movies.adapters.MovieAdapter
import com.jacobsheehy.best2016movies.presenters.MoviePresenter
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main and only Android activity (View) for this application. On start, prepare the UI
 * elements and load the data.
 */
class MainActivity : AppCompatActivity() {

    private val moviePresenter = MoviePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        loadJSON()
    }

    // use our Movie Presenter to load and display data
    private fun loadJSON() {
        moviePresenter.requestMovies(recyclerMovies, progressLoadingMovies)
    }

    // Initialize general properties about the RecyclerView, including
    // layout manager and data source (as well as listeners, adapters)
    private fun initRecyclerView() {
        recyclerMovies.setHasFixedSize(false)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerMovies.layoutManager = layoutManager
        recyclerMovies.adapter = MovieAdapter(moviePresenter.allMovies, moviePresenter, moviePresenter)

    }

}
