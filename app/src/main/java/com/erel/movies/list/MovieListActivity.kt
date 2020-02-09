package com.erel.movies.list

import com.erel.movies.base.BaseActivity

class MovieListActivity : BaseActivity() {
    override fun getFragmentInstance() = MovieListFragment.newInstance()
}