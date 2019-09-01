package ru.egoncharovsky.revolut.backendtest.transaction

import org.springframework.stereotype.Repository
import ru.egoncharovsky.revolut.backendtest.SimpleRepository

@Repository
class TransactionRepository : SimpleRepository<Transaction>(Transaction::class) {

    fun findByAccount(accountId: Long): Collection<Transaction> = entities.filterValues {
        it.from.id == accountId || it.to.id == accountId
    }.values

}