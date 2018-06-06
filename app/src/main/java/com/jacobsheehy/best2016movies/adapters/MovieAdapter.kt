package com.jacobsheehy.best2016movies.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.jacobsheehy.best2016movies.R
import com.jacobsheehy.best2016movies.models.Movie
import kotlinx.android.synthetic.main.item_movie.view.*
import java.text.SimpleDateFormat

class MovieAdapter(private val movieList: List<Movie>, private val listener :Listener) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        context = parent.context
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder:MovieViewHolder, position: Int) {
        holder.bind(movieList[position], listener, position)
    }

    interface Listener {
        fun onItemClicked(movie : Movie, position: Int, viewHolder: MovieViewHolder)
    }

    inner class MovieViewHolder (view : View) : RecyclerView.ViewHolder(view) {

        var isExpanded = false

        fun bind(movie : Movie, listener : Listener, position : Int) {
            itemView.textMovieDescription.text=movie.overview
            itemView.textPopularityMetric.text="${movie.popularity.toInt()}"
            Glide.with(itemView).load("http://image.tmdb.org/t/p/w500${movie.posterPath}").into(itemView.imageMoviePoster)
            itemView.textMovieTitle.text = movie.filmTitle
            val formatter = SimpleDateFormat("MMM d, yyyy")
            itemView.textReleaseDate.text=formatter.format(movie.releaseDate)
            itemView.buttonShowOverview.setOnClickListener{
                listener.onItemClicked(movie, position, this)
            }
            if(isExpanded) {
                expandOverview()
            } else {
                hideOverview()
            }

        }

        fun expandOverview() {
            itemView.textMovieDescription.visibility = View.VISIBLE
            itemView.buttonShowOverview.text=context?.getString(R.string.hide_overview)
            isExpanded = true

        }

        fun hideOverview() {
            itemView.textMovieDescription.visibility = View.GONE
            itemView.buttonShowOverview.text=context?.getString(R.string.show_overview)
            isExpanded = false
        }
    }

}