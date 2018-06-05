package com.jacobsheehy.best2016movies

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jacobsheehy.best2016movies.presenters.MoviePresenter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val BASE_URL = ""
    private var mCompositeDisposable: CompositeDisposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        mCompositeDisposable = CompositeDisposable()

        loadJSON()

    }

    fun loadJSON() {
        val presenter : MoviePresenter = MoviePresenter()
        presenter.requestMovies()
    }

    fun initRecyclerView() {
        recyclerMovies.setHasFixedSize(false)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerMovies.layoutManager = layoutManager
    }
}
