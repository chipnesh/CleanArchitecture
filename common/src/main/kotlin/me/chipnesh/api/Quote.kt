package me.chipnesh.api

expect class QuoteApi {
    suspend fun get(word: String): String
}