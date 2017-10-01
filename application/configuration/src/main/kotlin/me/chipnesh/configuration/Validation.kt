package me.chipnesh.configuration

import me.chipnesh.domain.account.ValidateRegisterAccountRequest
import me.chipnesh.domain.authentication.ValidateAuthenticateUserRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class Validation {
    @Bean
    open fun validateRegisterAccountRequest() = ValidateRegisterAccountRequest()

    @Bean
    open fun validateAuthenticateUserRequest() = ValidateAuthenticateUserRequest()
}