package com.erel.movies.domain.interactor

import com.erel.movies.domain.MockMoviesRepository
import com.erel.movies.domain.UnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchMoviesInteractorTest : UnitTest() {

    private lateinit var interactor: SearchMoviesInteractor

    override fun before() {
        super.before()
        interactor = SearchMoviesInteractor(MockMoviesRepository(), testDispatcher)
    }

    @Test
    fun `should return movies according to query`() = testScope.runBlockingTest {
        interactor.execute(SearchMoviesInteractor.Params("query1", 1)) { result ->
            result.fold(onSuccess = {
                it.getOrNull(0)?.let { movie ->
                    movie.id shouldEqual 1
                    movie.title shouldEqual "title1"
                } ?: assert(false)
            }, onFailure = {
                assert(false)
            })
        }
        interactor.execute(SearchMoviesInteractor.Params("query2", 1)) { result ->
            result.fold(onSuccess = {
                it.getOrNull(0)?.let { movie ->
                    movie.id shouldEqual 2
                    movie.title shouldEqual "title2"
                } ?: assert(false)
            }, onFailure = {
                assert(false)
            })
        }
    }

    @Test
    fun `should return errors correctly`() = testScope.runBlockingTest {
        interactor.execute(SearchMoviesInteractor.Params("query3", 1)) { result ->
            result.fold(onSuccess = {
                assert(false)
            }, onFailure = {
                it.message shouldEqual MockMoviesRepository.ERROR_MESSAGE
            })
        }
    }
}