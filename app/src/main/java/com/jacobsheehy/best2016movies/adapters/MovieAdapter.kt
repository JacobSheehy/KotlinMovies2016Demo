package com.jacobsheehy.best2016movies.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.jacobsheehy.best2016movies.R
import com.jacobsheehy.best2016movies.models.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(private val movieList: List<Movie>, private val listener :Listener) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        holder.bind(movieList[position], listener, position)
    }

    interface Listener {
        fun onItemClicked(movie : Movie)
    }

    class ViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        fun bind(movie : Movie, listener : Listener, position : Int) {
            itemView.textMovieDescription.text=movie.overview
            itemView.textPopularityMetric.text="${movie.popularity.toInt()}"
            Glide.with(itemView).load("http://image.tmdb.org/t/p/w500${movie.posterPath}").into(itemView.imageMoviePoster)
            itemView.textMovieTitle.text = movie.filmTitle
            itemView.buttonShowOverview.setOnClickListener{
                listener.onItemClicked(movie)
            }

        }
    }

}