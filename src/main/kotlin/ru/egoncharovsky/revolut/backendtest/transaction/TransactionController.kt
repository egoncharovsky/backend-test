package ru.egoncharovsky.revolut.backendtest.transaction

import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.egoncharovsky.revolut.backendtest.ReadController
import java.math.BigDecimal

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
    @ApiOperation("View all transfers in which this account participated",
            responseContainer = "Collection",
            response = Transaction::class)
    fun getOperationsHistory(@RequestBody accountId: Long): Collection<Transaction> {
        require(transactionRepository.find(accountId) != null) { "Account with id $accountId doesn't exist" }
        return transactionRepository.findByAccount(accountId)
    }
}