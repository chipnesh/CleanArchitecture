package me.chipnesh.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.env.get

@Configuration
open class ApplicationProperties(val environment: Environment) {

    val useEmail: Boolean
        get() = environment["notification.transport"] == "EMAIL"
}