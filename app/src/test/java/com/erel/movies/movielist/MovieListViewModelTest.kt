package com.erel.movies.movielist

import com.erel.movies.UnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieListViewModelTest : UnitTest() {

    private lateinit var viewModel: MovieListViewModel

    override fun before() {
        super.before()
        viewModel = MovieListViewModel(
            MockPlayingsInteractor(testDispatcher),
            MockSearchInteractor(testDispatcher),
            MovieMapper()
        )
        viewModel.moviesLiveData.observeForever {  }
    }

    @Test
    fun `should return playing movies with pagination`() = testScope.runBlockingTest {
        viewModel.searchMovies("")
        viewModel.moviesLiveData.value?.get(0)?.let {
            it.id shouldEqual 1
            it.title shouldEqual "title1"
        } ?: assert(false)
        viewModel.moviesLiveData.value?.loadAround(0)
        viewModel.moviesLiveData.value?.get(1)?.let {
            it.id shouldEqual 2
            it.title shouldEqual "title2"
        } ?: assert(false)
    }

    @Test
    fun `should return movies according to query`() = testScope.runBlockingTest {
        viewModel.searchMovies("query1")
        viewModel.moviesLiveData.value?.get(0)?.let {
            it.id shouldEqual 1
            it.title shouldEqual "title1"
        } ?: assert(false)
        viewModel.searchMovies("query2")
        viewModel.moviesLiveData.value?.get(0)?.let {
            it.id shouldEqual 2
            it.title shouldEqual "title2"
        } ?: assert(false)
    }
}