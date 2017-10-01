package me.chipnesh.domain

sealed class ValidationResult {
    class Valid : ValidationResult()
    class Invalid(val message: String) : ValidationResult()
}

fun check(condition: Boolean, message: String) =
        when (condition) {
            true -> ValidationResult.Valid()
            false -> ValidationResult.Invalid(message)
        }

fun ValidationResult.thenCheck(condition: Boolean, message: String) =
        when (condition) {
            true -> this
            false -> ValidationResult.Invalid(combineMessages(this, message))
        }

private fun combineMessages(validationResult: ValidationResult, message: String): String {
    return when (validationResult) {
        is ValidationResult.Valid -> message
        is ValidationResult.Invalid -> "${validationResult.message}, $message"
    }
}