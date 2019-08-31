package ru.egoncharovsky.revolut.backendtest.transaction

import ru.egoncharovsky.revolut.backendtest.SimpleRepository

class TransactionRepository : SimpleRepository<Transaction>(Transaction::class)