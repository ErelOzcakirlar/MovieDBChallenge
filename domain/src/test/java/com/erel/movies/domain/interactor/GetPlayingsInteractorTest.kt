package com.erel.movies.domain.interactor

import com.erel.movies.domain.MockMoviesRepository
import com.erel.movies.domain.MockMoviesRepository.Companion.ERROR_MESSAGE
import com.erel.movies.domain.UnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Test

@ExperimentalCoroutinesApi
class GetPlayingsInteractorTest : UnitTest() {

    private lateinit var interactor: GetPlayingsInteractor

    override fun before() {
        super.before()
        interactor = GetPlayingsInteractor(MockMoviesRepository(), testDispatcher)
    }

    @Test
    fun `should return playing movies correctly`() = testScope.runBlockingTest {
        interactor.execute(GetPlayingsInteractor.Params(1)) { result ->
            result.fold(onSuccess = {
                it.getOrNull(0)?.let { movie ->
                    movie.id shouldEqual 1
                } ?: assert(false)
            }, onFailure = {
                assert(false)
            })
        }
    }

    @Test
    fun `should return playing movies according to page number`() = testScope.runBlockingTest {
        interactor.execute(GetPlayingsInteractor.Params(2)) { result ->
            result.fold(onSuccess = {
                it.getOrNull(0)?.let { movie ->
                    movie.id shouldEqual 2
                } ?: assert(false)
            }, onFailure = {
                assert(false)
            })
        }
    }

    @Test
    fun `should return errors correctly`() = testScope.runBlockingTest {
        interactor.execute(GetPlayingsInteractor.Params(3)) { result ->
            result.fold(onSuccess = {
                assert(false)
            }, onFailure = {
                it.message shouldEqual ERROR_MESSAGE
            })
        }
    }
}