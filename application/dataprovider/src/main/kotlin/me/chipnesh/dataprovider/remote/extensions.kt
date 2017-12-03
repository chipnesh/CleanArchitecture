package me.chipnesh.dataprovider.remote

import org.springframework.web.coroutine.function.client.CoroutineWebClient
import java.util.*

typealias Request = CoroutineWebClient
inline suspend fun <reified T : Any> CoroutineWebClient.CoroutineResponseSpec.body(): T? = body(T::class.java)

val random = Random()
inline fun <reified T: Any> Array<T>.randomItem() = this[random.nextInt(this.size)]