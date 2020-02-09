package com.erel.movies.list

import androidx.recyclerview.widget.DiffUtil
import com.erel.movies.model.Movie

class MovieDiffCallback(private val oldMovies: List<Movie>, private val newMovies: List<Movie>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean =
        oldMovies[oldPosition].id == newMovies[newPosition].id

    override fun getOldListSize(): Int = oldMovies.size

    override fun getNewListSize(): Int = newMovies.size

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean =
        oldMovies[oldPosition] == newMovies[newPosition]

}