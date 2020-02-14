package com.erel.movies.movielist

import com.erel.movies.base.BaseActivity

class MovieListActivity : BaseActivity() {
    override fun getFragmentInstance() = MovieListFragment.newInstance()
}