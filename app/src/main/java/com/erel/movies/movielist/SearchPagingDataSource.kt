package com.erel.movies.movielist

import androidx.paging.PageKeyedDataSource
import com.erel.movies.domain.interactor.SearchMoviesInteractor
import com.erel.movies.model.Movie

class SearchPagingDataSource(
    private val searchQuery: String,
    private val movieMapper: MovieMapper,
    private val interactor: SearchMoviesInteractor
) : PageKeyedDataSource<Int, Movie>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        interactor.execute(
            SearchMoviesInteractor.Params(searchQuery, INITIAL_PAGE),
            successCallback = {
                callback.onResult(movieMapper.map(it), null, INITIAL_PAGE + 1)
            },
            failureCallback = {
                callback.onError(Throwable(it.message))
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        interactor.execute(
            SearchMoviesInteractor.Params(searchQuery, params.key),
            successCallback = {
                callback.onResult(movieMapper.map(it), params.key + 1)
            },
            failureCallback = {
                callback.onError(Throwable(it.message))
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        //No need for this case
    }

    companion object {
        private const val INITIAL_PAGE = 1
    }

}