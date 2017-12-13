package me.chipnesh.web

import kotlinext.js.asJsObject
import kotlinext.js.js
import kotlinext.js.jsObject
import kotlinext.js.toPlainObjectStripNull
import me.chipnesh.api.AccountInfoResult
import me.chipnesh.api.AuthenticationResult
import me.chipnesh.web.account.User
import me.chipnesh.web.account.User.Companion.ANON
import me.chipnesh.web.account.userReducer
import me.chipnesh.web.authentication.Session
import me.chipnesh.web.authentication.Session.Companion.EMPTY
import me.chipnesh.web.authentication.sessionReducer
import me.chipnesh.web.index.IndexComponent
import me.chipnesh.web.index.IndexProps
import me.chipnesh.web.index.rootMapper
import me.chipnesh.web.quote.QuotaComponent
import me.chipnesh.web.quote.QuotaProps
import me.chipnesh.web.quote.quotaMapper
import me.chipnesh.web.quote.quoteReducer
import me.chipnesh.web.wrappers.hmr.ReloadableApplication
import me.chipnesh.web.wrappers.redux.*
import me.chipnesh.web.wrappers.redux.Redux.applyMiddleware
import me.chipnesh.web.wrappers.router.History.createBrowserHistory
import me.chipnesh.web.wrappers.router.ReactRouterRedux.routerMiddleware
import me.chipnesh.web.wrappers.router.ReactRouterRedux.routerReducer
import me.chipnesh.web.wrappers.router.ReactRouterRedux.syncHistoryWithStore
import me.chipnesh.web.wrappers.router.connectedRouter
import me.chipnesh.web.wrappers.router.route
import me.chipnesh.web.wrappers.router.switch
import react.dom.render
import kotlin.browser.document

sealed class Action {
    data class Login(val result: AuthenticationResult) : Action()
    data class GetQuota(val quota: String) : Action()
    data class GetUser(val result: AccountInfoResult) : Action()
}

data class State(
        val quota: String = "",
        val user: User = ANON,
        val session: Session = EMPTY,
        val router: dynamic = js{}
)

class Application : ReloadableApplication<State>() {
    override val initState: State = jsObject { State() }

    override fun start(state: State) {

        val browserHistory = createBrowserHistory()
        val reducers = createReducers()
        val middlewares = createMiddlewares(browserHistory)
        val store = createStore(reducers, initState, middlewares)
        val history = syncHistoryWithStore(browserHistory, store)

        render(document.getElementById("root")) {
            provider(store) {
                connectedRouter(history) {
                    switch {
                        route("/", connect<IndexComponent, IndexProps>(rootMapper), true)
                        route("/quota", connect<QuotaComponent, QuotaProps>(quotaMapper))
                    }
                }
            }
        }

    }

    private fun createReducers(): Reducer<Any, Action> {
        return combine(
                "quota" to ::quoteReducer,
                "user" to ::userReducer,
                "session" to ::sessionReducer,
                "router" to ::routerReducer
        )
    }

    private fun createMiddlewares(history: dynamic): Enhancer<State> = composeWithDevTools(applyMiddleware<State>(
            thunkMiddleware,
            loggerMiddleware,
            actionTypeChecker,
            routerMiddleware(history)
    ))

    override fun dispose(): State = initState
}