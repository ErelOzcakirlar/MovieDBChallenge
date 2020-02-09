package com.erel.movies.domain.injection

import com.erel.movies.domain.interactor.AsyncExecutor
import com.erel.movies.domain.interactor.Executor
import com.erel.movies.domain.interactor.GetPlayingsInteractor
import com.erel.movies.domain.interactor.SearchMoviesInteractor
import org.koin.dsl.module

object DomainModule {
    fun create() = module {
        factory<Executor> { AsyncExecutor() }
        factory { GetPlayingsInteractor(get(), get()) }
        factory { SearchMoviesInteractor(get(), get()) }
    }
}