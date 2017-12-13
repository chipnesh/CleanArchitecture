package me.chipnesh.dataprovider.db

import me.chipnesh.domain.Account
import me.chipnesh.domain.account.Accounts
import org.springframework.data.mongodb.repository.CoroutineMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountsRepository : CoroutineMongoRepository<Account, String>, Accounts {

    override suspend fun save(account: Account) : Account

    override suspend fun findByLogin(login: String): Account?
}