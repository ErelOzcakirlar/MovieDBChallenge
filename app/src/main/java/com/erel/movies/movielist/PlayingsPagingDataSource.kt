package com.erel.movies.movielist

import androidx.paging.PageKeyedDataSource
import com.erel.movies.base.BaseMapper
import com.erel.movies.domain.interactor.BaseInteractor
import com.erel.movies.domain.interactor.GetPlayingsInteractor
import com.erel.movies.domain.model.MovieData
import com.erel.movies.model.Movie

class PlayingsPagingDataSource(
    private val mapper: BaseMapper<MovieData, Movie>,
    private val interactor: BaseInteractor<GetPlayingsInteractor.Params, List<MovieData>>
) : PageKeyedDataSource<Int, Movie>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        interactor.execute(
            GetPlayingsInteractor.Params(INITIAL_PAGE),
            successCallback = {
                callback.onResult(mapper.map(it), null, INITIAL_PAGE + 1)
            },
            failureCallback = {
                callback.onError(Throwable(it.message))
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        interactor.execute(
            GetPlayingsInteractor.Params(params.key),
            successCallback = {
                callback.onResult(mapper.map(it), params.key + 1)
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