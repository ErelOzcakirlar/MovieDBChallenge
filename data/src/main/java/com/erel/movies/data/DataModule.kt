package com.erel.movies.data

import com.erel.movies.domain.repository.MoviesRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val QUERY_PARAM = "api_key"
    private const val API_KEY = "2696829a81b1b5827d515ff121700838"

    fun create() = module {
        factory<MoviesRepository> { MoviesRepositoryImpl(provideMoviesService()) }
    }

    private fun provideClient() = OkHttpClient.Builder()
        .addInterceptor {
            val request = it.request()
            val newUrl = request.url().newBuilder().addQueryParameter(QUERY_PARAM, API_KEY).build()
            it.proceed(request.newBuilder().url(newUrl).build())
        }
        .addInterceptor(HttpLoggingInterceptor().apply { level = BODY })
        .build()

    private fun provideRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun provideMoviesService() =
        provideRetrofit().create(MoviesService::class.java)
}