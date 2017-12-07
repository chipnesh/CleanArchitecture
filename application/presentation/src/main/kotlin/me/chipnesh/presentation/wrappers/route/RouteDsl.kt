package me.chipnesh.presentation.wrappers.route

import react.*

fun RBuilder.router(history: dynamic, handler: RHandler<RProps>) = child(RouterComponent::class) {
    attrs {
        this.history = history
    }
    handler()
}

fun RBuilder.browserRouter(handler: RHandler<RProps>) = child(BrowserRouterComponent::class, handler)

fun RBuilder.switch(handler: RHandler<RProps>) = child(SwitchComponent::class, handler)

fun RBuilder.route(path: String, component: RClass<*>, exact: Boolean = false) =
        child(RouteComponent::class) {
            attrs {
                this.path = path
                this.exact = exact
                this.component = component
            }
        }

fun RBuilder.routeLink(to: String, handler: RHandler<RProps>) = child(LinkComponent::class) {
    attrs {
        this.to = to
    }
    handler()
}