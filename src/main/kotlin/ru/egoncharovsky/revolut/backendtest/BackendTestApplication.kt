package ru.egoncharovsky.revolut.backendtest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BackendTestApplication

fun main(args: Array<String>) {
    runApplication<BackendTestApplication>(*args)
}
