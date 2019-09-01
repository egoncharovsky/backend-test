package ru.egoncharovsky.revolut.backendtest.transaction

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/backend-test/transaction")
class TransactionController(
        @Autowired private val transactionService: TransactionService
) {

    @PostMapping("/transfer-money")
    fun transferMoney(transfer: TransferMoneyDto) {
        transactionService.transferMoney(transfer.fromAccountId, transfer.toAccountId, transfer.amount)
    }

    data class TransferMoneyDto(
            val fromAccountId: Long,
            val toAccountId: Long,
            val amount: BigDecimal
    )
}