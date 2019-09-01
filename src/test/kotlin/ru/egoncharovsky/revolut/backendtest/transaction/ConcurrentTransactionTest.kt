package ru.egoncharovsky.revolut.backendtest.transaction

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Repeat
import org.springframework.test.context.junit4.SpringRunner
import ru.egoncharovsky.revolut.backendtest.Repository
import ru.egoncharovsky.revolut.backendtest.account.Account
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.test.assertEquals

@RunWith(SpringRunner::class)
@SpringBootTest
class ConcurrentTransactionTest {

    private val pool = Executors.newFixedThreadPool(20);

    @Autowired
    lateinit var transactionService: TransactionService

    @Autowired
    lateinit var accountRepo: Repository<Account>

    @Test
    @Repeat(10)
    fun `transferring money is threadsafe`() {
        val account1id = accountRepo.save(Account(null, 100.toBigDecimal())).id!!
        val account2id = accountRepo.save(Account(null, 100.toBigDecimal())).id!!

        val tasks = (0 until 100).map {
            Callable {
                transactionService.transferMoney(account1id, account2id, 0.5.toBigDecimal())
            }
        } + (0 until 100).map {
            Callable {
                transactionService.transferMoney(account2id, account1id, 0.4.toBigDecimal())
            }
        }.shuffled()

        pool.invokeAll(tasks)

        assertEquals(accountRepo.get(account1id).balance, 90.0.toBigDecimal())
        assertEquals(accountRepo.get(account2id).balance, 110.0.toBigDecimal())
    }
}