package com.jacobsheehy.best2016movies

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jacobsheehy.best2016movies.adapters.MovieAdapter
import com.jacobsheehy.best2016movies.presenters.MoviePresenter
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.SimpleItemAnimator



class MainActivity : AppCompatActivity() {

    val moviePresenter = MoviePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        loadJSON()
    }

    fun loadJSON() {
        moviePresenter.requestMovies(recyclerMovies)
    }

    fun initRecyclerView() {
        recyclerMovies.setHasFixedSize(false)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerMovies.layoutManager = layoutManager
        recyclerMovies.adapter = MovieAdapter(moviePresenter.allMovies, moviePresenter, moviePresenter)

    }

}
