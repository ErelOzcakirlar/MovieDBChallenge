package com.erel.movies.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.erel.movies.databinding.RowItemMovieBinding
import com.erel.movies.model.Movie

class MovieListAdapter : PagedListAdapter<Movie, MovieViewHolder>(MovieDiffCallback()) {

    var itemClickListener: (Movie) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) =
        MovieViewHolder(
            RowItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, itemClickListener)
        }
    }

}

class MovieViewHolder(private val binding: RowItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie, clickListener: (Movie) -> Unit) {
        binding.movie = item
        binding.executePendingBindings()
        binding.root.setOnClickListener { clickListener(item) }
    }
}

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem == newItem

}
