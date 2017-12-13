package me.chipnesh.web.wrappers.axios

import kotlinext.js.jsObject
import kotlin.browser.document
import kotlin.coroutines.experimental.suspendCoroutine
import kotlin.js.Promise

@JsModule("axios")
external fun <T> request(config: RequestConfig): Promise<Response<T>>

external interface RequestConfig {
    var url: String
    var method: String
    var baseUrl: String
    var timeout: Number
    var data: dynamic
    var transferRequest: dynamic
    var transferResponse: dynamic
    var headers: dynamic
    var params: dynamic
    var withCredentials: Boolean
    var adapter: dynamic
    var auth: dynamic
    var responseType: String
    var xsrfCookieName: String
    var xsrfHeaderName: String
    var onUploadProgress: dynamic
    var onDownloadProgress: dynamic
    var maxContentLength: Number
    var validateStatus: (Number) -> Boolean
    var maxRedirects: Number
    var httpAgent: dynamic
    var httpsAgent: dynamic
    var proxy: dynamic
    var cancelToken: dynamic
}

external interface Response<T> {
    val data: T
    val status: Number
    val statusText: String
    val headers: dynamic
    val config: RequestConfig
}

fun get(path: String, config: RequestConfig.() -> Unit = {}): RequestConfig {
    val settings = jsObject<RequestConfig> {
        method = "GET"
        timeout = 3000
        url = document.location!!.origin + path
        params = js("({})")
        data = js("({})")
    }
    settings.config()
    return settings.apply { data = JSON.stringify(data) }
}

fun post(path: String, body: dynamic = null, config: RequestConfig.() -> Unit = {}): RequestConfig {
    val settings = jsObject<RequestConfig> {
        method = "POST"
        timeout = 3000
        url = document.location!!.origin + path
        params = js("({})")
        data = body ?: js("({})")
    }
    settings.config()
    return settings.apply { data = JSON.stringify(data) }
}

suspend inline fun <reified T> Promise<Response<T>>.execute() : T {
    return suspendCoroutine { c ->
        this.then { response ->
            console.log(response)
            c.resume(response.data)
        }.catch { error ->
            console.log(error)
            c.resumeWithException(RuntimeException("HTTP error: ${error}"))
        }
    }
}