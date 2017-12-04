package me.chipnesh.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {
    companion object {
        val log: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)
    }
    @ExceptionHandler(Throwable::class)
    fun handler(t: Throwable) = log.error(t.message, t)
}