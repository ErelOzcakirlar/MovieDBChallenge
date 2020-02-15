package com.erel.movies.movielist

import androidx.paging.DataSource
import com.erel.movies.domain.interactor.GetPlayingsInteractor
import com.erel.movies.domain.interactor.SearchMoviesInteractor
import com.erel.movies.domain.model.Failure
import com.erel.movies.model.Movie

class MovieDataSourceFactory(
    private val searchQuery: String,
    private val movieMapper: MovieMapper,
    private val searchInteractor: SearchMoviesInteractor,
    private val playingsInteractor: GetPlayingsInteractor,
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