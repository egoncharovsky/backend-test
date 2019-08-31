package ru.egoncharovsky.revolut.backendtest.account

import org.springframework.stereotype.Repository
import ru.egoncharovsky.revolut.backendtest.SimpleRepository

@Repository
class AccountRepository : SimpleRepository<Account>(Account::class)