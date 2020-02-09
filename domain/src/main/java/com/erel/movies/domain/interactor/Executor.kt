package com.erel.movies.domain.interactor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

interface Executor {
    fun execute(context: CoroutineContext, block: suspend CoroutineScope.() -> Unit)
}

class AsyncExecutor : Executor {

    @Synchronized
    override fun execute(context: CoroutineContext, block: suspend CoroutineScope.() -> Unit) {
        GlobalScope.launch(context) { block() }
    }

}