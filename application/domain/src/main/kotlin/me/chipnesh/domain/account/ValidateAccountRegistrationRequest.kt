package me.chipnesh.domain.account

import me.chipnesh.domain.ValidationResult
import me.chipnesh.domain.check
import me.chipnesh.domain.thenCheck

class ValidateAccountRegistrationRequest {

    fun validate(request: RegisterAccountRequest): ValidationResult {
        return check(request.login.isNotBlank(), "Login is empty")
                .thenCheck(request.name.isNotBlank(), "Name is empty")
                .thenCheck(request.email.isNotBlank(), "Email is empty")
                .thenCheck(request.password.length >= 6, "Password should contain at least 6 chars")
    }
}