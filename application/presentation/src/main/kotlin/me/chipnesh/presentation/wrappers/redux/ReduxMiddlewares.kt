package me.chipnesh.presentation.wrappers.redux

import me.chipnesh.presentation.wrappers.js.require

val ReduxThunk: dynamic = require("redux-thunk").default
val composeWithDevTools: dynamic = require("redux-devtools-extension").composeWithDevTools
val loggerMiddleware = require("redux-logger").createLogger()