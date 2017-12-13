package me.chipnesh.web

import kotlinext.js.jsObject
import me.chipnesh.web.account.User
import me.chipnesh.web.authentication.Session.Companion.EMPTY
import me.chipnesh.web.account.User.Companion.ANON
import me.chipnesh.web.account.userReducer
import me.chipnesh.web.authentication.Session
import me.chipnesh.web.authentication.sessionReducer
import me.chipnesh.web.index.Action
import me.chipnesh.web.index.IndexProps
import me.chipnesh.web.index.IndexComponent
import me.chipnesh.web.index.rootMapper
import me.chipnesh.web.quote.quoteReducer
import me.chipnesh.web.wrappers.hmr.ReloadableApplication
import me.chipnesh.web.wrappers.redux.*
import me.chipnesh.web.wrappers.redux.Redux.applyMiddleware
import react.dom.render
import kotlin.browser.document

data class State(
        val quota: String = "",
        val user: User = ANON,
        val session: Session = EMPTY
)

class Application : ReloadableApplication<State>() {
    override val initState: State = jsObject { State() }

    override fun start(state: State) {
        val store = createStore (createReducers(), state, createMiddlewares())

        render(document.getElementById("root")) {
            provider(store) {
                connect<IndexComponent, IndexProps>(rootMapper)
            }
        }

    }

    private fun createReducers(): Reducer<Any, Action> {
        return combine(
                "quota" to ::quoteReducer,
                "user" to ::userReducer,
                "session" to ::sessionReducer
        )
    }

    private fun createMiddlewares(): Enhancer<State> = composeWithDevTools(applyMiddleware<State>(
            thunkMiddleware,
            loggerMiddleware,
            actionTypeChecker
    ))

    override fun dispose(): State = initState
}