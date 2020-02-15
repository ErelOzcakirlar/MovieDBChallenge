package com.erel.movies.domain.injection

import com.erel.movies.domain.interactor.*
import org.koin.dsl.module

object DomainModule {
    fun create() = module {
        factory { GetPlayingsInteractor(get()) }
        factory { SearchMoviesInteractor(get()) }
    }
}