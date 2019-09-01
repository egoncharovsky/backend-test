package ru.egoncharovsky.revolut.backendtest.account

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.egoncharovsky.revolut.backendtest.CrudController

@RestController
@RequestMapping("/backend-test/account")
class AccountController(
        @Autowired private val accountRepository: AccountRepository
) : CrudController<Account>(accountRepository)