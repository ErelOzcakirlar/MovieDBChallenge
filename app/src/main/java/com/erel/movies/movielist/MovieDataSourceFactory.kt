package com.erel.movies.movielist

import androidx.paging.DataSource
import com.erel.movies.domain.interactor.BaseInteractor
import com.erel.movies.domain.interactor.GetPlayingsInteractor
import com.erel.movies.domain.interactor.SearchMoviesInteractor
import com.erel.movies.domain.model.Failure
import com.erel.movies.domain.model.MovieData
import com.erel.movies.model.Movie

class MovieDataSourceFactory(
    private val searchQuery: String,
    private val movieMapper: MovieMapper,
    private val searchInteractor: BaseInteractor<SearchMoviesInteractor.Params, List<MovieData>>,
    private val playingsInteractor: BaseInteractor<GetPlayingsInteractor.Params, List<MovieData>>,
    private val failureCallback: (Throwable) -> Unit
) : DataSource.Factory<Int, Movie>() {

    override fun create(): DataSource<Int, Movie> {
        return if (searchQuery.isEmpty()) {
            PlayingsPagingDataSource(movieMapper, playingsInteractor, failureCallback)
        } else {
            SearchPagingDataSource(searchQuery, movieMapper, searchInteractor, failureCallback)
        }
    }
}