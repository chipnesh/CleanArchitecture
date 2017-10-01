package me.chipnesh.domain.authentication

import me.chipnesh.domain.ValidationResult
import me.chipnesh.domain.check
import me.chipnesh.domain.thenCheck

class ValidateAuthenticateUserRequest {
    fun validate(request: AuthenticateUserRequest): ValidationResult {
        return check(request.login.isNotBlank(), "Login is empty")
                .thenCheck(request.password.length >= 6, "Password should contain at least 6 chars")
    }
}