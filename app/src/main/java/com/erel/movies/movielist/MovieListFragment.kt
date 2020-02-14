package com.erel.movies.movielist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.lifecycle.Observer
import com.erel.movies.R
import com.erel.movies.base.BaseFragment
import com.erel.movies.moviedetail.MovieDetailActivity
import com.erel.movies.model.Movie
import com.erel.movies.util.hideKeyboard
import kotlinx.android.synthetic.main.fragment_movie_list.*
import org.koin.android.ext.android.inject

class MovieListFragment : BaseFragment() {

    override val viewModel: MovieListViewModel by inject()
    private val moviesAdapter: MovieListAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        moviesAdapter.itemClickListener = ::navigateToMovieDetail
        viewModel.searchMovies("")
    }

    override fun getLayoutResource() = R.layout.fragment_movie_list

    override fun initView(view: View) {
        recyclerMovies.adapter = moviesAdapter
    }

    override fun observeViewModel() {
        super.observeViewModel()
        with(viewModel) {
            moviesLiveData.observe(viewLifecycleOwner, Observer {
                moviesAdapter.submitList(it)
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_movie_list, menu)
        (menu.findItem(R.id.searchView)?.actionView as? SearchView)?.apply {
            imeOptions = imeOptions or EditorInfo.IME_FLAG_NO_EXTRACT_UI
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    hideKeyboard()
                    return true
                }

                override fun onQueryTextChange(query: String): Boolean {
                    viewModel.searchMovies(query)
                    return true
                }
            })
        }
    }

    private fun navigateToMovieDetail(movie: Movie) {
        startActivity(MovieDetailActivity.callingIntent(requireContext(), movie))
    }

    companion object {
        fun newInstance() = MovieListFragment()
    }

}