package me.chipnesh.presentation.wrappers.route

import react.*
import kotlin.reflect.KClass

fun RBuilder.router(handler: RHandler<RProps>) = child(BrowserRouterComponent::class, handler)

fun RBuilder.switch(handler: RHandler<RProps>) = child(SwitchComponent::class, handler)

fun RBuilder.route(path: String, component: KClass<out React.Component<*, *>>, exact: Boolean = false) =
        child(RouteComponent::class) {
            attrs {
                this.path = path
                this.exact = exact
                this.component = component.js.unsafeCast<RClass<RProps>>()
            }
        }

fun RBuilder.routeLink(to: String, handler: RHandler<RProps>) = child(LinkComponent::class) {
    attrs {
        this.to = to
    }
    handler()
}

fun RBuilder.connectedRouter(history: dynamic, handler: RHandler<RProps>) =
        child(ConnectedRouterComponent::class) {
            attrs {
                this.history = history
            }
            this.handler()
        }