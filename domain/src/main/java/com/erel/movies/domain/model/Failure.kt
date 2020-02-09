package com.erel.movies.domain.model

open class Failure(val message:String? = null)

class ServerFailure(message: String?):Failure(message)

class ConnectionFailure:Failure()