package com.erel.movies.domain.interactor

import com.erel.movies.domain.model.ErrorResponse
import com.erel.movies.domain.model.Failure
import com.erel.movies.domain.model.Response
import com.erel.movies.domain.model.ServerFailure
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

abstract class BaseInteractor<T, R>(private val asyncContext: CoroutineContext) {

    @ExperimentalCoroutinesApi
    fun execute(
        params: T,
        successCallback: (R) -> Unit,
        failureCallback: (Failure) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Default) {
            call(params)
                .flowOn(asyncContext)
                .mapLatest { it.results!! }
                .catch { exception ->
                    when (exception) {
                        is HttpException -> {
                            val serverError = exception.response()?.errorBody()?.string()
                            if (serverError.isNullOrEmpty()) {
                                failureCallback(ServerFailure(exception.message()))
                            } else {
                                val errorResponse =
                                    Gson().fromJson(serverError, ErrorResponse::class.java)
                                val message =
                                    errorResponse.message ?: errorResponse.errors?.getOrNull(0)
                                failureCallback(ServerFailure(message))
                            }
                        }
                        else -> failureCallback(Failure(exception.message))
                    }
                }
                .first()
                .let(successCallback)
        }
    }

    abstract fun call(params: T): Flow<Response<R>>
}
