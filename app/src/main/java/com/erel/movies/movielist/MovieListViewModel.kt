package com.erel.movies.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.erel.movies.base.BaseViewModel
import com.erel.movies.domain.interactor.BaseInteractor
import com.erel.movies.domain.interactor.GetPlayingsInteractor
import com.erel.movies.domain.interactor.SearchMoviesInteractor
import com.erel.movies.domain.model.MovieData
import com.erel.movies.model.Movie

class MovieListViewModel(
    private val getPlayingsInteractor: BaseInteractor<GetPlayingsInteractor.Params, List<MovieData>>,
    private val searchMoviesInteractor: BaseInteractor<SearchMoviesInteractor.Params, List<MovieData>>,
    private val mapper: MovieMapper
) : BaseViewModel() {

    private var searchQueryLiveData = MutableLiveData<String>()

    val moviesLiveData: LiveData<PagedList<Movie>>

    init {
        val config = with(PagedList.Config.Builder()) {
            setPageSize(PAGE_SIZE)
            setInitialLoadSizeHint(PAGE_SIZE)
            setPrefetchDistance(PREFETCH_DISTANCE)
            build()
        }
        moviesLiveData = Transformations.switchMap(searchQueryLiveData) { query ->
            val factory = MovieDataSourceFactory(
                query,
                mapper,
                searchMoviesInteractor,
                getPlayingsInteractor,
                failureCallback = {
                    errorLiveData.postValue(it)
                })
            LivePagedListBuilder<Int, Movie>(factory, config).build()
        }
    }

    fun searchMovies(query: String) {
        searchQueryLiveData.value = query
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val PREFETCH_DISTANCE = 5
    }
}