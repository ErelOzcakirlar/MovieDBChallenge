package com.erel.movies.domain.model

open class Failure(override val message:String? = null): Throwable(message)

class ServerFailure(override val message: String):Failure(message)

class ConnectionFailure:Failure()