package me.chipnesh.presentation.wrappers.redux

import kotlinext.js.js

open class ReduxAction(private val payload: Any) {
    operator fun invoke(): dynamic {
        val type = this::class.simpleName
        return js {
            this.type = type
            this.payload = payload
        }
    }
}