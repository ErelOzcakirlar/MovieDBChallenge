package com.erel.movies.list

import androidx.lifecycle.MutableLiveData
import com.erel.movies.base.BaseMapper
import com.erel.movies.base.BaseViewModel
import com.erel.movies.domain.interactor.BaseInteractor
import com.erel.movies.domain.interactor.Executor
import com.erel.movies.domain.interactor.GetPlayingsInteractor
import com.erel.movies.domain.interactor.SearchMoviesInteractor
import com.erel.movies.domain.model.MovieData
import com.erel.movies.domain.model.Response
import com.erel.movies.list.MovieMapper.Companion.INVALID_ID
import com.erel.movies.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

class MovieListViewModel(
    private val getPlayingsInteractor: GetPlayingsInteractor,
    private val searchMoviesInteractor: SearchMoviesInteractor,
    private val mapper: MovieMapper,
    private val executor: Executor
) : BaseViewModel() {

    private var waitingQuery = ""
    private var currentPage = INITIAL_PAGE
    private var totalPages = INITIAL_PAGE
    private var isSearchActive = false

    val hasNextPage: Boolean
        get() = currentPage < totalPages

    val isLoading: Boolean
        get() = progressLiveData.value == true

    val moviesLiveData = MutableLiveData<List<Movie>>()

    fun getPlayings() {
        isSearchActive = false
        progressLiveData.value = true
        getPlayingsInteractor.execute(
            GetPlayingsInteractor.Params(INITIAL_PAGE),
            ::handleMovies,
            ::handleFailure
        )
    }

    fun searchMovies(query: String) {
        waitingQuery = query
        executor.execute(Dispatchers.IO) {
            delay(DEBOUNCE_PERIOD_IN_MILLIS)
            if (query == waitingQuery) {
                isSearchActive = true
                searchMoviesInteractor.execute(
                    SearchMoviesInteractor.Params(query, INITIAL_PAGE),
                    ::handleMovies,
                    ::handleFailure
                )
            }
        }
    }

    fun getNextPage() {
        if (isSearchActive) {
            searchMoviesInteractor.execute(
                SearchMoviesInteractor.Params(waitingQuery, currentPage + 1),
                ::handleMovies,
                ::handleFailure
            )
        } else {
            getPlayingsInteractor.execute(
                GetPlayingsInteractor.Params(currentPage + 1),
                ::handleMovies,
                ::handleFailure
            )
        }
    }

    private fun handleMovies(response: Response<List<MovieData>>) {
        progressLiveData.postValue(false)
        val filteredResults =
            response.results?.let { mapper.map(it) }?.filter { it.id != INVALID_ID }
        if (response.page == INITIAL_PAGE) {
            moviesLiveData.postValue(filteredResults)
        } else {
            val newValue = ArrayList<Movie>()
            newValue.addAll(moviesLiveData.value ?: listOf())
            newValue.addAll(filteredResults ?: listOf())
            moviesLiveData.postValue(newValue)
        }
        response.page?.let {
            currentPage = it
        }
        response.totalPages?.let {
            totalPages = it
        }
    }

    companion object {
        private const val INITIAL_PAGE = 1
        private const val DEBOUNCE_PERIOD_IN_MILLIS = 1000L
    }
}