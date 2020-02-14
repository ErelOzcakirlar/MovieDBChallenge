package com.erel.movies.domain.interactor

import com.erel.movies.domain.model.ConnectionFailure
import com.erel.movies.domain.model.ErrorResponse
import com.erel.movies.domain.model.Failure
import com.erel.movies.domain.model.Response
import com.erel.movies.domain.model.ServerFailure
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException

abstract class BaseInteractor<T, R>(private val executor: Executor) {

    fun execute(params: T, successCallback: (R) -> Unit, failureCallback: (Failure) -> Unit) {
        executor.execute(Dispatchers.IO) {
            try {
                val response = call(params)
                executor.execute(Dispatchers.Default) {
                    successCallback(response.results!!)
                }
            } catch (exception: Exception) {
                executor.execute(Dispatchers.Default) {
                    when (exception) {
                        is HttpException -> {
                            val serverError = exception.response()?.errorBody()?.string()
                            if (serverError.isNullOrEmpty()) {
                                failureCallback(ServerFailure(exception.message()))
                            } else {
                                val errorResponse = Gson().fromJson(serverError, ErrorResponse::class.java)
                                val message = errorResponse.message ?: errorResponse.errors?.getOrNull(0)
                                failureCallback(ServerFailure(message))
                            }
                        }
                        else -> failureCallback(Failure(exception.message))
                    }
                }
            }
        }
    }

    abstract suspend fun call(params: T): Response<R>
}