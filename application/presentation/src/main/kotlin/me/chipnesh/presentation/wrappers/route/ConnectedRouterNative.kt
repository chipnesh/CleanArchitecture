@file:JsModule("connected-react-router")

package me.chipnesh.presentation.wrappers.route

import react.RState
import react.React
import react.ReactElement

@JsName("routerMiddleware")
external fun routerMiddleware(history: dynamic): dynamic

@JsName("connectRouter")
external fun connectRouter(history: dynamic): (dynamic) -> dynamic

@JsName("ConnectedRouter")
external class ConnectedRouterComponent : React.Component<ConnectedRouterProps, RState> {
    override fun render(): ReactElement?
}