package me.chipnesh.domain

import java.time.LocalDateTime

data class Session(
        val id: String,
        val login: String,
        val active: Boolean,
        val expirationTime: LocalDateTime
) {
    fun isExpired() = expirationTime.isBefore(LocalDateTime.now())
}

data class Account(
        val id: String,
        val login: String,
        val name: String,
        val email: String,
        val passwordHash: String
)