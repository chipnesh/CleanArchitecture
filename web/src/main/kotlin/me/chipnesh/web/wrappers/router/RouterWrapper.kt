package me.chipnesh.web.wrappers.router

import me.chipnesh.web.wrappers.redux.Middleware
import me.chipnesh.web.wrappers.redux.Redux
import react.RProps
import react.RState
import react.React
import react.ReactElement

@JsModule("react-router-dom")
@JsNonModule
external object ReactRouterDom {
    @JsName("HashRouter")
    class HashRouterComponent : React.Component<RProps, RState> {
        override fun render(): ReactElement?
    }

    @JsName("Switch")
    class SwitchComponent : React.Component<RProps, RState> {
        override fun render(): ReactElement?
    }

    @JsName("Route")
    class RouteComponent : React.Component<RouteProps, RState> {
        override fun render(): ReactElement?
    }

    @JsName("Link")
    class LinkComponent : React.Component<LinkProps, RState> {
        override fun render(): ReactElement?
    }

    interface RouteProps : RProps {
        var path: String
        var exact: Boolean
        var component: dynamic
    }

    interface LinkProps : RProps {
        var to: String
    }

    interface RouteResultProps<T : RProps> : RProps {
        var match: RouteResultMatch<T>
    }

    interface RouteResultMatch<T : RProps> {
        var url: String
        var path: String
        var params: T
    }
}

@JsModule("react-router-redux")
@JsNonModule
external object ReactRouterRedux {

    @JsName("routerReducer")
    fun routerReducer(state: Any, action: Any): Any

    @JsName("routerMiddleware")
    fun routerMiddleware(history: dynamic): Middleware

    @JsName("syncHistoryWithStore")
    fun syncHistoryWithStore(history: dynamic, store: dynamic)

    @JsName("push")
    fun push(path: String): Any
}

@JsModule("connected-react-router")
@JsNonModule
external object ConnectedReactRouter {

    @JsName("ConnectedRouter")
    class ConnectedRouterComponent : React.Component<ConnectedRouterProps, RState> {
        override fun render(): ReactElement?
    }

    interface ConnectedRouterProps : RProps {
        var history: dynamic
        var location: dynamic
        var action: dynamic
    }
}

@JsModule("history")
@JsNonModule
external object History {
    @JsName("createBrowserHistory")
    fun createBrowserHistory(): dynamic
}