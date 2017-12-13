package me.chipnesh.domain

sealed class ValidationResult {
    class Valid : ValidationResult()
    class Invalid(val messages: List<String>) : ValidationResult()
}

fun check(condition: Boolean, message: String) =
        when (condition) {
            true -> ValidationResult.Valid()
            false -> ValidationResult.Invalid(listOf(message))
        }

fun ValidationResult.thenCheck(condition: Boolean, message: String) =
        when (condition) {
            true -> this
            false -> when(this) {
                is ValidationResult.Valid -> ValidationResult.Invalid(listOf(message))
                is ValidationResult.Invalid -> ValidationResult.Invalid(this.messages + message)
            }
        }