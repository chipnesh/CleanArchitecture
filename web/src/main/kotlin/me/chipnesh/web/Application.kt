package me.chipnesh.web

import me.chipnesh.api.AccountInfoResult
import me.chipnesh.api.AuthenticationResult
import me.chipnesh.web.account.User
import me.chipnesh.web.account.User.Companion.ANON
import me.chipnesh.web.account.userReducer
import me.chipnesh.web.authentication.Session
import me.chipnesh.web.authentication.Session.Companion.EMPTY
import me.chipnesh.web.authentication.sessionReducer
import me.chipnesh.web.index.IndexComponent
import me.chipnesh.web.index.indexMapper
import me.chipnesh.web.quote.QuotaComponent
import me.chipnesh.web.quote.quotaMapper
import me.chipnesh.web.quote.quoteReducer
import me.chipnesh.web.wrappers.hmr.ReloadableApplication
import me.chipnesh.web.wrappers.redux.*
import me.chipnesh.web.wrappers.redux.Redux.applyMiddleware
import me.chipnesh.web.wrappers.router.*
import me.chipnesh.web.wrappers.router.ReactRouterRedux.routerMiddleware
import me.chipnesh.web.wrappers.router.ReactRouterRedux.routerReducer
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
        val session: Session = EMPTY
)

class Application : ReloadableApplication<State>() {
    override val initState: State = State()

    override fun start(state: State) {

        val reducers = createReducers()
        val middlewares = createMiddlewares()
        val store = createStore(reducers, state, middlewares)
        render(document.getElementById("root")) {
            provider(store) {
                connectedRouter(history) {
                    switch {
                        route("/", connect<IndexComponent>(indexMapper), true)
                        route("/quota", connect<QuotaComponent>(quotaMapper))
                    }
                }
            }
        }

    }

    private fun createReducers(): Reducer<Any, Action> = combine(
            "quota" to ::quoteReducer,
            "user" to ::userReducer,
            "session" to ::sessionReducer,
            "router" to ::routerReducer
    )

    private fun createMiddlewares(): Enhancer<State> = composeWithDevTools(applyMiddleware<State>(
            thunkMiddleware,
            loggerMiddleware,
            actionTypeChecker,
            routerMiddleware(history)
    ))

    override fun dispose(): State = initState
}