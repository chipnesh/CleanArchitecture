package me.chipnesh.presentation.wrappers.axios

import kotlinext.js.jsObject
import kotlin.browser.document
import kotlin.coroutines.experimental.Continuation
import kotlin.js.Promise

@JsModule("axios")
external fun <T> axios(config: AxiosConfigSettings): Promise<AxiosResponse<T>>

external interface AxiosConfigSettings {
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

external interface AxiosResponse<T> {
    val data: T
    val status: Number
    val statusText: String
    val headers: dynamic
    val config: AxiosConfigSettings
}

fun get(path: String, block: AxiosConfigSettings.() -> Unit = {}): AxiosConfigSettings {
    val settings = jsObject<AxiosConfigSettings> {
        method = "GET"
        timeout = 3000
        url = document.location!!.origin + path
        params = js("({})")
    }
    settings.block()
    return settings
}

fun post(path: String, block: AxiosConfigSettings.() -> Unit = {}): AxiosConfigSettings {
    val settings = jsObject<AxiosConfigSettings> {
        method = "POST"
        timeout = 3000
        url = document.location!!.origin + path
        params = js("({})")
    }
    settings.block()
    return settings
}

fun <T> Promise<AxiosResponse<T>>.execute(c: Continuation<T>) {
    this.then { response ->
        console.log(response)
        c.resume(response.data)
    }.catch { error ->
        console.log(error)
        c.resumeWithException(RuntimeException("HTTP error: ${error}"))
    }
}