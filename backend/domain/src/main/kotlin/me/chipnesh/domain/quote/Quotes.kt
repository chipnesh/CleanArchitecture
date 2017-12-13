package me.chipnesh.domain.quote

import me.chipnesh.domain.Quote

interface Quotes {

    suspend fun findByWord(word: String): Quote?
}