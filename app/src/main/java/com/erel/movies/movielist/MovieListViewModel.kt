package com.erel.movies.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.erel.movies.base.BaseMapper
import com.erel.movies.base.BaseViewModel
import com.erel.movies.domain.interactor.BaseInteractor
import com.erel.movies.domain.interactor.Executor
import com.erel.movies.domain.interactor.GetPlayingsInteractor
import com.erel.movies.domain.interactor.SearchMoviesInteractor
import com.erel.movies.domain.model.MovieData
import com.erel.movies.domain.model.Response
import com.erel.movies.movielist.MovieMapper.Companion.INVALID_ID
import com.erel.movies.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

class MovieListViewModel(
    private val getPlayingsInteractor: BaseInteractor<GetPlayingsInteractor.Params, List<MovieData>>,
    private val searchMoviesInteractor: BaseInteractor<SearchMoviesInteractor.Params, List<MovieData>>,
    private val mapper: BaseMapper<MovieData, Movie>
) : BaseViewModel() {

    private var searchQueryLiveData = MutableLiveData<String>()

    val moviesLiveData: LiveData<PagedList<Movie>>

    init {
        val config = PagedList.Config.Builder().build()
        moviesLiveData = Transformations.switchMap(searchQueryLiveData) { query ->
            val factory =
                MovieDataSourceFactory(query, mapper, searchMoviesInteractor, getPlayingsInteractor)
            LivePagedListBuilder<Int, Movie>(factory, config).build()
        }
    }

    fun searchMovies(query: String) {
        searchQueryLiveData.value = query
    }
}