package ru.egoncharovsky.revolut.backendtest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
@EnableSwagger2
class BackendTestApplication

fun main(args: Array<String>) {
    runApplication<BackendTestApplication>(*args)
}
