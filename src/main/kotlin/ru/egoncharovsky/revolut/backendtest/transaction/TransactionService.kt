package ru.egoncharovsky.revolut.backendtest.transaction

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.egoncharovsky.revolut.backendtest.Repository
import ru.egoncharovsky.revolut.backendtest.account.Account
import java.math.BigDecimal

@Service
class TransactionService(
        @Autowired private val accountRepository: Repository<Account>,
        @Autowired private val transactionRepository: Repository<Transaction>
) {

    fun transferMoney(accountFromId: Long, accountToId: Long, amount: BigDecimal): Transaction {
        require(accountFromId != accountToId) { "From and to account must be different" }

        val from = accountRepository.get(accountFromId)
        val to = accountRepository.get(accountToId)

        val transaction = Transaction(
                null,
                accountRepository.get(accountFromId),
                accountRepository.get(accountToId),
                amount,
                null
        )

        if (accountFromId < accountToId) {
            synchronized(from) {
                synchronized(to) {
                    return performTransfer(transaction)
                }
            }
        } else {
            synchronized(to) {
                synchronized(from) {
                    return performTransfer(transaction)
                }
            }
        }
    }

    private fun performTransfer(transaction: Transaction): Transaction {
        val performed = transaction.perform()

        accountRepository.save(performed.from)
        accountRepository.save(performed.to)
        return transactionRepository.save(transaction)
    }
}