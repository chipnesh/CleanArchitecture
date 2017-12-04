package me.chipnesh.presentation.common.route

import react.*

@JsModule("react-router-redux")
external object RouterReduxNative {
    fun routerMiddleware(vararg history: dynamic): dynamic
    fun routerReducer(): dynamic
}

@JsModule("history/createBrowserHistory")
external object BrowserHistoryNative {
    val default: dynamic
}

@JsName("ConnectedRouter")
external class ConnectedRouterComponent : React.Component<ConnectedRouterProps, RState> {
    override fun render(): ReactElement?
}

external interface ConnectedRouterProps : RProps {
    var history: dynamic
}

fun RBuilder.connectedRouter(history: dynamic, handler: RHandler<RProps>) =
        child(ConnectedRouterComponent::class) {
            attrs {
                this.history = history
            }
            this.handler()
        }

