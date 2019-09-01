package ru.egoncharovsky.revolut.backendtest.transaction

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import ru.egoncharovsky.revolut.backendtest.Repository
import ru.egoncharovsky.revolut.backendtest.account.Account
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@RunWith(SpringRunner::class)
@SpringBootTest
class TransactionServiceTest {

    @Autowired
    lateinit var accountRepo: Repository<Account>

    @Autowired
    lateinit var transactionService: TransactionService

    @Test
    fun `Can not transfer money from not existed account`() {
        val to = accountRepo.save(Account(null, 1.toBigDecimal()))

        assertFailsWith(
                IllegalArgumentException::class,
                "${Account::class} with id 0 does not exist"
        ) {
            transactionService.transferMoney(0, to.id!!, 1.toBigDecimal())
        }
    }

    @Test
    fun `Can not transfer money to not existed account`() {
        val from = accountRepo.save(Account(null, 1.toBigDecimal()))

        assertFailsWith(
                IllegalArgumentException::class,
                "${Account::class} with id 0 does not exist"
        ) {
            transactionService.transferMoney(from.id!!, 0, 1.toBigDecimal())
        }
    }

    @Test
    fun `Transfer can not make account balance negative`() {
        val from = accountRepo.save(Account(null, 0.toBigDecimal()))
        val to = accountRepo.save(Account(null, 0.toBigDecimal()))

        assertFailsWith(
                IllegalArgumentException::class,
                "Balance can't be negative"
        ) {
            transactionService.transferMoney(from.id!!, to.id!!, 1.toBigDecimal())
        }
    }

    @Test
    fun `Money success transfer`() {
        val from = accountRepo.save(Account(null, 10.toBigDecimal()))
        val to = accountRepo.save(Account(null, 12.toBigDecimal()))

        transactionService.transferMoney(from.id!!, to.id!!, 5.34.toBigDecimal())

        assertEquals(4.66.toBigDecimal(), accountRepo.get(from.id!!).balance)
        assertEquals(17.34.toBigDecimal(), accountRepo.get(to.id!!).balance)
    }

    @Test
    fun `Transfer to the same account is restricted`() {
        val fromTo = accountRepo.save(Account(null, 10.toBigDecimal()))

        assertFailsWith(
                IllegalArgumentException::class,
                "From and to account must be different"
        ) {
            transactionService.transferMoney(fromTo.id!!, fromTo.id!!, 1.toBigDecimal())
        }
    }
}