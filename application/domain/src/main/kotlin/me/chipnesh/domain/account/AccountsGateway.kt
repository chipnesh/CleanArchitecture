package me.chipnesh.domain.account

import me.chipnesh.domain.Account

interface AccountsGateway {
    fun findByLogin(login: String) : Account?
    fun add(account: Account) : String
}