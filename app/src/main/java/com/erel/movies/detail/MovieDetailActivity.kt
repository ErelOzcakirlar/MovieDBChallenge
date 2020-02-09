package com.erel.movies.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.erel.movies.base.BaseActivity
import com.erel.movies.detail.MovieDetailFragment.Companion.BUNDLE_KEY_MOVIE
import com.erel.movies.model.Movie

class MovieDetailActivity : BaseActivity() {

    override fun getFragmentInstance() =
        MovieDetailFragment.newInstance(intent.getParcelableExtra(BUNDLE_KEY_MOVIE))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        fun callingIntent(context: Context, movie: Movie) =
            Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(BUNDLE_KEY_MOVIE, movie)
            }
    }
}