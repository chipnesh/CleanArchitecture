package me.chipnesh.configuration

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = arrayOf("me.chipnesh.configuration"))
open class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}