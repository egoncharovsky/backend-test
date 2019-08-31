package ru.egoncharovsky.revolut.backendtest.transaction

import ru.egoncharovsky.revolut.backendtest.account.Account
import java.math.BigDecimal

data class Transaction(
        val from: Account,
        val to: Account,
        val amount: BigDecimal
) {

    init {
        require(amount >= BigDecimal.ZERO) {
            "Amount can't be negative"
        }
    }
}