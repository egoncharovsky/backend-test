package ru.egoncharovsky.revolut.backendtest.transaction

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/backend-test")
class TransactionController {

    @PostMapping("/test")
    fun test() = "Test successful!"

}