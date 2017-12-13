package me.chipnesh.presentation.wrappers.redux

import me.chipnesh.presentation.wrappers.js.require

val thunkMiddleware: dynamic = require("redux-thunk").default
val composeWithDevTools: dynamic = require("redux-devtools-extension").composeWithDevTools
val loggerMiddleware = require("redux-logger").createLogger()
val actionTypeChecker = actionTypeChecker()

typealias Middleware = () -> ((dynamic) -> dynamic) -> (dynamic) -> dynamic

private fun actionTypeChecker(): Middleware = {
    { next ->
        { action ->
            val actionWrapper = js("({})")
            actionWrapper["action"] = action
            actionWrapper["type"] = action::class.simpleName
            next(actionWrapper as Any)
        }
    }
}