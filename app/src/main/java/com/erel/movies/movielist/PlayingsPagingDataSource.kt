package com.erel.movies.movielist

import androidx.paging.PageKeyedDataSource
import com.erel.movies.domain.interactor.GetPlayingsInteractor
import com.erel.movies.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi

class PlayingsPagingDataSource(
    private val mapper: MovieMapper,
    private val interactor: GetPlayingsInteractor,
    private val failureCallback: (Throwable) -> Unit
) : PageKeyedDataSource<Int, Movie>() {

    @ExperimentalCoroutinesApi
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        interactor.execute(GetPlayingsInteractor.Params(INITIAL_PAGE)) { result ->
            result.fold(onSuccess = {
                callback.onResult(mapper.map(it), null, INITIAL_PAGE + 1)
            }, onFailure = {
                failureCallback(it)
            })
        }
    }

    @ExperimentalCoroutinesApi
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        interactor.execute(GetPlayingsInteractor.Params(params.key)) { result ->
            result.fold(onSuccess = {
                callback.onResult(mapper.map(it), params.key + 1)
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