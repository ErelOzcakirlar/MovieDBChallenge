package com.erel.movies.movielist

import androidx.paging.PageKeyedDataSource
import com.erel.movies.domain.interactor.SearchMoviesInteractor
import com.erel.movies.domain.model.Failure
import com.erel.movies.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi

class SearchPagingDataSource(
    private val searchQuery: String,
    private val movieMapper: MovieMapper,
    private val interactor: SearchMoviesInteractor,
    private val failureCallback: (Throwable) -> Unit
) : PageKeyedDataSource<Int, Movie>() {

    @ExperimentalCoroutinesApi
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        interactor.execute(SearchMoviesInteractor.Params(searchQuery, INITIAL_PAGE)) { result ->
            result.fold(onSuccess = {
                callback.onResult(movieMapper.map(it), null, INITIAL_PAGE + 1)
            }, onFailure = {
                failureCallback(it)
            })
        }
    }

    @ExperimentalCoroutinesApi
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        interactor.execute(SearchMoviesInteractor.Params(searchQuery, params.key)) { result ->
            result.fold(onSuccess = {
                callback.onResult(movieMapper.map(it), params.key + 1)
            }, onFailure = {
                failureCallback(it)
            })
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        //No need for this case
    }

    companion object {
        private const val INITIAL_PAGE = 1
    }

}