package me.chipnesh.domain

import java.math.BigInteger
import java.security.MessageDigest

fun String.md5Hash(): String {
    val bytes = MessageDigest
            .getInstance("MD5")
            .digest(this.toByteArray())

    return BigInteger(1, bytes)
            .toString(16)
            .padStart(32, '0')
}