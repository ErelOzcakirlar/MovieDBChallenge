package com.erel.movies.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.*
import org.amshove.kluent.shouldEqual
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesRepositoryImplTest : UnitTest() {

    private lateinit var repository: MoviesRepositoryImpl


    override fun before() {
        super.before()
        repository = MoviesRepositoryImpl(MockMoviesService())
    }

    @Test
    fun `should return playing movies correctly`() = testScope.runBlockingTest {
            repository.getPlayings(1).collect {
                it.results?.getOrNull(0)?.let { movie ->
                    movie.id shouldEqual 1
                } ?: assert(false)
            }
        }

    @Test
    fun `should return playing movies according to page number`() = testScope.runBlockingTest {
        repository.getPlayings(2).collect {
            it.results?.getOrNull(0)?.let { movie ->
                movie.id shouldEqual 2
            } ?: assert(false)
        }
    }

    @Test
    fun `should return movies according to query`() = testScope.runBlockingTest {
        repository.getBySearch("query1", 1).collect {
            it.results?.getOrNull(0)?.let { movie ->
                movie.id shouldEqual 1
                movie.title shouldEqual "title1"
            } ?: assert(false)
        }
        repository.getBySearch("query2", 1).collect {
            it.results?.getOrNull(0)?.let { movie ->
                movie.id shouldEqual 2
                movie.title shouldEqual "title2"
            } ?: assert(false)
        }
    }

}