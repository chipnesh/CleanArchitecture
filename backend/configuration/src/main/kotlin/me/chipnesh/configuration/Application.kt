package me.chipnesh.configuration

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.kotlin.experimental.coroutine.EnableCoroutine

@SpringBootApplication(
        scanBasePackages = [
            "me.chipnesh.configuration",
            "me.chipnesh.dataprovider.db"
        ],
        exclude = [EmbeddedMongoAutoConfiguration::class]
)
@EnableCoroutine
open class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}