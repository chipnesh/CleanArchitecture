package me.chipnesh.domain.account

import me.chipnesh.domain.Account

interface Accounts {

    suspend fun findByLogin(login: String) : Account?

    suspend fun save(account: Account) : Account
}