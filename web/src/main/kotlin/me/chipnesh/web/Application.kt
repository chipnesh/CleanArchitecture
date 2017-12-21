package me.chipnesh.web

import me.chipnesh.web.account.User
import me.chipnesh.web.account.User.Companion.ANON
import me.chipnesh.web.account.userReducer
import me.chipnesh.web.authentication.AuthenticationPage
import me.chipnesh.web.authentication.Session
import me.chipnesh.web.authentication.Session.Companion.EMPTY
import me.chipnesh.web.authentication.authenticationMapper
import me.chipnesh.web.authentication.sessionReducer
import me.chipnesh.web.index.IndexPage
import me.chipnesh.web.index.indexMapper
import me.chipnesh.web.quote.QuotaPage
import me.chipnesh.web.quote.quotaMapper
import me.chipnesh.web.quote.quoteReducer
import me.chipnesh.web.wrappers.hmr.ReloadableApplication
import me.chipnesh.web.wrappers.material.materialUI
import me.chipnesh.web.wrappers.redux.*
import me.chipnesh.web.wrappers.redux.Redux.applyMiddleware
import me.chipnesh.web.wrappers.router.*
import me.chipnesh.web.wrappers.router.ReactRouterRedux.routerMiddleware
import me.chipnesh.web.wrappers.router.ReactRouterRedux.routerReducer
import react.dom.render
import kotlin.browser.document

interface Action

data class State(
        val quote: String = "",
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
            materialUI {
                redux(store) {
                    router(history) {
                        connectRedux<IndexPage>(indexMapper) {
                            pageSwitcher {
                                route("/auth", connectRedux<AuthenticationPage>(authenticationMapper))
                                route("/quote", connectRedux<QuotaPage>(quotaMapper))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun createReducers(): Reducer<Any, Action> = combine(
            "quote" to ::quoteReducer,
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