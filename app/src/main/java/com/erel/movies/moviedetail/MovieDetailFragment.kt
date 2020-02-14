package com.erel.movies.moviedetail

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.erel.movies.R
import com.erel.movies.base.BaseFragment
import com.erel.movies.base.BaseViewModel
import com.erel.movies.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : BaseFragment() {

    override val viewModel = BaseViewModel()

    override fun getLayoutResource() = R.layout.fragment_movie_detail

    override fun initView(view: View) {
        val binding = FragmentMovieDetailBinding.bind(view)
        binding.movie = arguments?.getParcelable(BUNDLE_KEY_MOVIE)
        binding.executePendingBindings()
    }

    companion object {
        const val BUNDLE_KEY_MOVIE = "movie"
        fun newInstance(movie: Parcelable?) =
            MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(BUNDLE_KEY_MOVIE, movie)
                }
            }
    }
}