package ru.egoncharovsky.revolut.backendtest.transaction

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.egoncharovsky.revolut.backendtest.Repository
import ru.egoncharovsky.revolut.backendtest.account.Account
import java.math.BigDecimal

@Service
class TransactionService(
        @Autowired val accountRepository: Repository<Account>,
        @Autowired val transactionRepository: Repository<Transaction>
) {

    fun transferMoney(accountFromId: Long, accountToId: Long, amount: BigDecimal) {
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
                    performTransfer(transaction)
                }
            }
        } else {
            synchronized(to) {
                synchronized(from) {
                    performTransfer(transaction)
                }
            }
        }
    }

    private fun performTransfer(transaction: Transaction) {
        val performed = transaction.perform()

        accountRepository.save(performed.from)
        accountRepository.save(performed.to)
        transactionRepository.save(transaction)
    }
}