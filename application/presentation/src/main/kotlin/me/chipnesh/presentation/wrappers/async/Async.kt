package me.chipnesh.presentation.wrappers.async

import kotlin.js.Promise
import kotlin.coroutines.experimental.*

fun launch(block: suspend () -> Unit) {
    async(block).catch { exception -> console.log("Failed with $exception") }
}

public fun <T> async(c: suspend () -> T): Promise<T> {
    return Promise { resolve, reject ->
        c.startCoroutine(object : Continuation<T> {
            override fun resume(value: T) = resolve(value)

            override fun resumeWithException(exception: Throwable) = reject(exception)

            override val context = EmptyCoroutineContext
        })
    }
}

public suspend fun <T> Promise<T>.await() = suspendCoroutine<T> { c ->
    then({ c.resume(it) }, { c.resumeWithException(it) })
}