package com.erel.movies.movielist

import androidx.paging.DataSource
import com.erel.movies.domain.interactor.GetPlayingsInteractor
import com.erel.movies.domain.interactor.SearchMoviesInteractor
import com.erel.movies.model.Movie

class MovieDataSourceFactory(
    private val searchQuery: String,
    private val movieMapper: MovieMapper,
    private val searchInteractor: SearchMoviesInteractor,
    private val playingsInteractor: GetPlayingsInteractor
) : DataSource.Factory<Int, Movie>() {

    override fun create(): DataSource<Int, Movie> {
        return if (searchQuery.isEmpty()) {
            PlayingsPagingDataSource(movieMapper, playingsInteractor)
        } else {
            SearchPagingDataSource(searchQuery, movieMapper, searchInteractor)
        }
    }
}