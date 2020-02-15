package com.erel.movies.domain.interactor

import com.erel.movies.domain.model.ServerFailure
import com.erel.movies.domain.model.ConnectionFailure
import com.erel.movies.domain.model.Failure
import com.erel.movies.domain.model.Response
import com.erel.movies.domain.model.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext

abstract class BaseInteractor<T, R>(private val asyncContext: CoroutineContext) {

    @ExperimentalCoroutinesApi
    fun execute(
        params: T,
        callback: (Result<R>) -> Unit
    ) {
        runBlocking {
            call(params)
                .flowOn(asyncContext)
                .map { Result.success(it.results!!) }
                .catch { exception ->
                    when (exception) {
                        is HttpException -> {
                            val serverError = exception.response()?.errorBody()?.string()
                            if (serverError.isNullOrEmpty()) {
                                emit(Result.failure(ServerFailure(exception.message())))
                            } else {
                                val errorResponse = Gson()
                                    .fromJson(serverError, ErrorResponse::class.java)
                                val message = errorResponse.message
                                    ?: errorResponse.errors?.getOrNull(0)
                                emit(Result.failure(
                                    message?.let {
                                        ServerFailure(message)
                                    } ?: Failure()))
                            }
                        }
                        is UnknownHostException -> emit(Result.failure(ConnectionFailure()))
                        else -> emit(Result.failure(Failure(exception.message)))
                    }
                }
                .collect { callback(it) }
        }
    }

    abstract fun call(params: T): Flow<Response<R>>
}
