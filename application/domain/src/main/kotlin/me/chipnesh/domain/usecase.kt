package me.chipnesh.domain

interface UseCase<in Request, out Response> {
    fun execute(request: Request): Result<Response>
}

sealed class Result<out T>(val success: Boolean) {
    data class Success<out T>(val result: T): Result<T>(true) {
        constructor() : this(Unit as T)
    }
    data class Failed(val messages: List<String>): Result<Nothing>(false) {
        constructor(message: String) : this(listOf(message))
    }
}