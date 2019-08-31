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
        val timestamp: LocalDateTime?
) : Entity {

    init {
        require(amount >= BigDecimal.ZERO) {
            "Amount can't be negative"
        }
    }

    fun perform(): Transaction = copy(
            from = from.copy(balance = from.balance - amount),
            to = to.copy(balance = to.balance + amount),
            timestamp = LocalDateTime.now()
    )

}