package me.chipnesh.api

data class RegistrationForm(
        val login: String = "",
        val name: String = "",
        val email: String = "",
        val password: String = ""
)

sealed class RegistrationResult(val success: Boolean) {
    data class Success(val id: String): RegistrationResult(true)
    data class Failed(val messages: List<String>): RegistrationResult(false)
}
sealed class AccountInfoResult(val success: Boolean) {
    data class Success(val login: String, val name: String, val email: String) : AccountInfoResult(true)
    data class Failed(val messages: List<String>) : AccountInfoResult(false)
}

expect class AccountApi {
    suspend fun register(form: RegistrationForm): RegistrationResult

    suspend fun info(login: String): AccountInfoResult
}