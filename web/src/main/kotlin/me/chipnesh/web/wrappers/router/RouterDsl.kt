package me.chipnesh.web.wrappers.router

import react.RBuilder
import react.RClass
import react.RHandler
import react.RProps

fun RBuilder.hashRouter(handler: RHandler<RProps>) = child(ReactRouterDom.HashRouterComponent::class, handler)

fun RBuilder.pageSwitcher(handler: RHandler<RProps>) = child(ReactRouterDom.SwitchComponent::class, handler)

fun RBuilder.route(path: String, component: dynamic, exact: Boolean = false) =
        child(ReactRouterDom.RouteComponent::class) {
            attrs {
                this.path = path
                this.exact = exact
                this.component = component.unsafeCast<RClass<RProps>>()
            }
        }

fun RBuilder.routeLink(to: String, handler: RHandler<RProps>) = child(ReactRouterDom.LinkComponent::class) {
    attrs {
        this.to = to
    }
    handler()
}

fun RBuilder.router(history: dynamic, handler: RHandler<RProps>) = child(ReactRouterRedux.ConnectedRouterComponent::class) {
    attrs {
        this.history = history
    }
    this.handler()
}