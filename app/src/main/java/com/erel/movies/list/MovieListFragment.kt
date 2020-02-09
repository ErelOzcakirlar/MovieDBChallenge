package com.erel.movies.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.erel.movies.R
import com.erel.movies.base.BaseFragment
import com.erel.movies.detail.MovieDetailActivity
import com.erel.movies.model.Movie
import com.erel.movies.util.PaginationScrollListener
import kotlinx.android.synthetic.main.fragment_movie_list.*
import org.koin.android.ext.android.inject

class MovieListFragment : BaseFragment() {

    override val viewModel: MovieListViewModel by inject()
    private val moviesAdapter: MovieListAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        moviesAdapter.itemClickListener = ::navigateToMovieDetail
        viewModel.getPlayings()
    }

    override fun getLayoutResource() = R.layout.fragment_movie_list

    override fun initView(view: View) {
        with(recyclerMovies) {
            val manager = LinearLayoutManager(requireContext())
            adapter = moviesAdapter
            layoutManager = manager
            addOnScrollListener(object : PaginationScrollListener(manager) {
                override fun isLastPage() = !viewModel.hasNextPage
                override fun isLoading() = viewModel.isLoading
                override fun loadMoreItems() = viewModel.getNextPage()
            })
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()
        with(viewModel) {
            moviesLiveData.observe(viewLifecycleOwner, Observer {
                moviesAdapter.setData(it ?: listOf())
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_movie_list, menu)
        (menu.findItem(R.id.searchView)?.actionView as? SearchView)?.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?) = true

                override fun onQueryTextChange(query: String): Boolean {
                    if (query.isEmpty()) {
                        viewModel.getPlayings()
                    } else {
                        viewModel.searchMovies(query)
                    }
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