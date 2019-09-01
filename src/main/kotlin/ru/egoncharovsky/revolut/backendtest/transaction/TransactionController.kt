package ru.egoncharovsky.revolut.backendtest.transaction

import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.egoncharovsky.revolut.backendtest.ReadController
import java.math.BigDecimal
import java.time.LocalDateTime

@RestController
@RequestMapping("/transaction")
class TransactionController(
        @Autowired private val transactionService: TransactionService,
        @Autowired private val transactionRepository: TransactionRepository
) : ReadController<Transaction>(transactionRepository) {

    @PostMapping("/transfer-money/{fromAccountId}/{toAccountId}")
    @ApiOperation("Transfers money between accounts")
    fun transferMoney(
            @PathVariable fromAccountId: Long,
            @PathVariable toAccountId: Long,
            @RequestBody amount: BigDecimal
    ): Transaction {
        return transactionService.transferMoney(fromAccountId, toAccountId, amount)
    }

    @GetMapping("/history/{accountId}")
    @ApiOperation("View all balance changes for account",
            responseContainer = "Collection",
            response = AccountOperation::class)
    fun getOperationsHistory(@PathVariable accountId: Long): Collection<AccountOperation> {
        require(transactionRepository.find(accountId) != null) { "Account with id $accountId doesn't exist" }
        return transactionRepository.findByAccount(accountId).map {
            AccountOperation.fromTransaction(accountId, it)
        }
    }

    data class AccountOperation(
            val transactionId: Long,
            val balanceChange: BigDecimal,
            val secondAccount: Long,
            var timestamp: LocalDateTime
    ) {
        companion object {
            fun fromTransaction(accountId: Long, transaction: Transaction): AccountOperation = when (accountId) {
                transaction.from.id -> AccountOperation(
                        transaction.id!!,
                        -transaction.amount,
                        transaction.to.id!!,
                        transaction.timestamp!!
                )
                transaction.to.id -> AccountOperation(
                        transaction.id!!,
                        transaction.amount,
                        transaction.from.id!!,
                        transaction.timestamp!!
                )
                else -> throw IllegalArgumentException(
                        "Transaction ${transaction.id} had not operate with account $accountId"
                )
            }
        }
    }
}