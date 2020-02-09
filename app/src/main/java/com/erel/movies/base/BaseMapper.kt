package com.erel.movies.base

abstract class BaseMapper<DataClass, UIClass> {
    fun map(data: List<DataClass>): List<UIClass> = data.map { map(it) }

    abstract fun map(data: DataClass): UIClass

}