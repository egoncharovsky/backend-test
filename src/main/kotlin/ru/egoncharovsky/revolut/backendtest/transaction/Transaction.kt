package ru.egoncharovsky.revolut.backendtest.transaction

import ru.egoncharovsky.revolut.backendtest.Entity
import ru.egoncharovsky.revolut.backendtest.account.Account
import java.math.BigDecimal
import java.time.LocalDateTime

data class Transaction(
        override var id: Long?,
        val from: Account,
        val to: Account,
        val amount: BigDecimal,
        var timestamp: LocalDateTime?
) : Entity {

    init {
        require(amount >= BigDecimal.ZERO) {
            "Amount can't be negative"
        }
    }

    fun perform(): Transaction {
        from.balance -= amount
        to.balance += amount
        timestamp = LocalDateTime.now()
        return this
    }

    fun isExecuted(): Boolean = timestamp != null
}