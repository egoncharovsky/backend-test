package ru.egoncharovsky.revolut.backendtest.account

import ru.egoncharovsky.revolut.backendtest.Entity
import java.math.BigDecimal

data class Account(
        override var id: Long?,

        var balance: BigDecimal
) : Entity {

    init {
        require(balance >= BigDecimal.ZERO) { "Balance can't be negative" }
    }
}