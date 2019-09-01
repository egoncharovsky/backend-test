package ru.egoncharovsky.revolut.backendtest.transaction

import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/backend-test/transaction")
class TransactionController(
        @Autowired private val transactionService: TransactionService
) {

    @PostMapping("/transfer-money/{fromAccountId}/{toAccountId}")
    @ApiOperation("Transfers money between accounts")
    fun transferMoney(
            @PathVariable fromAccountId: Long,
            @PathVariable toAccountId: Long,
            @RequestBody amount: BigDecimal
    ) {
        transactionService.transferMoney(fromAccountId, toAccountId, amount)
    }
}