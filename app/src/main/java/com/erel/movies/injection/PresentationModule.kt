package com.erel.movies.injection

import com.erel.movies.list.MovieListAdapter
import com.erel.movies.list.MovieListViewModel
import com.erel.movies.list.MovieMapper
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PresentationModule {
    fun create() = module {
        viewModel { MovieListViewModel(get(), get(), get(), get()) }
        factory { MovieListAdapter() }
        factory { MovieMapper() }
    }
}