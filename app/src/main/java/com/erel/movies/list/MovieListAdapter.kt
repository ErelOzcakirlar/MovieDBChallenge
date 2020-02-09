package com.erel.movies.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.erel.movies.databinding.RowItemMovieBinding
import com.erel.movies.model.Movie

class MovieListAdapter : RecyclerView.Adapter<MovieViewHolder>() {

    private var data = listOf<Movie>()

    var itemClickListener: (Movie) -> Unit = {}

    fun setData(newData: List<Movie>) {
        val diffResult = DiffUtil.calculateDiff(MovieDiffCallback(data, newData))
        data = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) =
        MovieViewHolder(RowItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(data[position], itemClickListener)
    }
}

class MovieViewHolder(private val binding: RowItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie, clickListener: (Movie) -> Unit) {
        binding.movie = item
        binding.executePendingBindings()
        binding.root.setOnClickListener { clickListener(item) }
    }
}
