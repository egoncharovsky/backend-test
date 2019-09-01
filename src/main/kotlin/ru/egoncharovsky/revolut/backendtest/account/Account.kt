package ru.egoncharovsky.revolut.backendtest.account

import ru.egoncharovsky.revolut.backendtest.Entity
import java.math.BigDecimal

data class Account(
        override var id: Long?
) : Entity {
    var balance: BigDecimal = BigDecimal.ZERO
        set(value) {
            require(value >= BigDecimal.ZERO) { "Balance can't be negative (account id $id)" }
            field = value
        }

    constructor(id: Long?, balance: BigDecimal) : this(id) {
        this.balance = balance
    }
}