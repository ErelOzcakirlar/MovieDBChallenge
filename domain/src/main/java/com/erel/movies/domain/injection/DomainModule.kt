package com.erel.movies.domain.injection

import com.erel.movies.domain.interactor.*
import com.erel.movies.domain.model.MovieData
import org.koin.dsl.module

object DomainModule {
    fun create() = module {
        factory<Executor> { AsyncExecutor() }
        factory { GetPlayingsInteractor(get(), get()) }
        factory { SearchMoviesInteractor(get(), get()) }
    }
}