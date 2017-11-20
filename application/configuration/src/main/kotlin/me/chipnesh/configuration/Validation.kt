package me.chipnesh.configuration

import me.chipnesh.domain.account.ValidateAccountRegistrationRequest
import me.chipnesh.domain.authentication.ValidateAuthenticationUserRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class Validation {
    @Bean
    open fun validateRegisterAccountRequest() = ValidateAccountRegistrationRequest()

    @Bean
    open fun validateAuthenticateUserRequest() = ValidateAuthenticationUserRequest()
}