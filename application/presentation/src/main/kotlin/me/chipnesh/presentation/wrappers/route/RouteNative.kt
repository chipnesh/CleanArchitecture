@file:JsModule("react-router-dom")
package me.chipnesh.presentation.wrappers.route

import me.chipnesh.presentation.wrappers.react.redux.DispatchProp
import react.*

@JsName("BrowserRouter")
external class BrowserRouterComponent : React.Component<RProps, RState> {
    override fun render(): ReactElement?
}

@JsName("Switch")
external class SwitchComponent : React.Component<RProps, RState> {
    override fun render(): ReactElement?
}

@JsName("Route")
external class RouteComponent : React.Component<RouteProps, RState> {
    override fun render(): ReactElement?
}

@JsName("Link")
external class LinkComponent : React.Component<LinkProps, RState> {
    override fun render(): ReactElement?
}

external interface RouteProps : RProps {
    var path: String
    var exact: Boolean
    var component: RClass<*>
}

external interface LinkProps : RProps {
    var to: String
}

external interface RouteResultProps<S, T : RProps> : DispatchProp<S> {
    var match: RouteResultMatch<T>
}

external interface RouteResultMatch<T : RProps> {
    var url: String
    var path: String
    var params: T
}