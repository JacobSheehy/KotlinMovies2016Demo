package com.jacobsheehy.best2016movies.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.jacobsheehy.best2016movies.R
import com.jacobsheehy.best2016movies.models.Movie
import com.jacobsheehy.best2016movies.presenters.MoviePresenter
import kotlinx.android.synthetic.main.item_movie.view.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * This is the data adapter between the raw list of Movie objects and the UI views inside
 * the RecyclerView
 */
class MovieAdapter(private val movieList: List<Movie>, private val listener :Listener, var moviePresenter: MoviePresenter) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var context: Context? = null

    // Create the Movie item view holder and capture the parent context
    // Context required for use in accessing string resources.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        context = parent.context
        return MovieViewHolder(view)
    }

    // Return the number of items in the list
    override fun getItemCount(): Int {
        return movieList.size
    }

    // Bind a data item to a view holder
    override fun onBindViewHolder(holder:MovieViewHolder, position: Int) {
        holder.bind(movieList[position], listener, position)
    }

    // Contract interface for button clicks on a Show or hide overview button
    interface Listener {
        fun onItemClicked(movie : Movie, position: Int, viewHolder: MovieViewHolder)
    }


    // View Holder for Movie objects
    inner class MovieViewHolder (view : View) : RecyclerView.ViewHolder(view) {

        private val baseImageUrl = "http://image.tmdb.org/t/p/w200"

        // Show the data contained in this Movie object onto this View
        fun bind(movie : Movie, listener : Listener, position : Int) {
            itemView.textMovieDescription.text=movie.overview
            itemView.textPopularityMetric.text="${movie.popularity.toInt()}"
            // Use Glide to load (and cache) images
            Glide.with(itemView).load("$baseImageUrl${movie.posterPath}").into(itemView.imageMoviePoster)
            itemView.textMovieTitle.text = movie.filmTitle
            itemView.textReleaseDate.text = getFormattedDate(movie)
            itemView.buttonShowOverview.setOnClickListener{
                listener.onItemClicked(movie, position, this)
            }

            when(movie.isExpanded) {
                true ->  { showExpandedUI() }
                false -> { showHiddenUI() }
                null ->  { showHiddenUI() }
            }
        }

        private fun getFormattedDate(movie: Movie) : String {
            val formatter = SimpleDateFormat("MMM d, yyyy", Locale.US) // fair to assume US locale for now
            return formatter.format(movie.releaseDate)
        }

        private fun showExpandedUI() {
            itemView.textMovieDescription.visibility = View.VISIBLE
            itemView.buttonShowOverview.text=context?.getString(R.string.hide_overview)
        }

        private fun showHiddenUI() {
            itemView.textMovieDescription.visibility = View.GONE
            itemView.buttonShowOverview.text=context?.getString(R.string.show_overview)
        }
    }

}