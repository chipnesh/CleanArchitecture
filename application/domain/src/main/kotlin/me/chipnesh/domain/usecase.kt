package me.chipnesh.domain

interface UseCase<in Request, out Response> {
    fun execute(request: Request): Result<Response>
}

sealed class Result<out T>(val success: Boolean) {
    data class Success<out T>(val result: T): Result<T>(true)
    data class Failed(val message: String): Result<Nothing>(false)
}