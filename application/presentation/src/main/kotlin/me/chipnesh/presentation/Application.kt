package me.chipnesh.presentation

import kotlinext.js.jsObject
import me.chipnesh.presentation.Session.Companion.EMPTY
import me.chipnesh.presentation.User.Companion.ANON
import me.chipnesh.presentation.components.AppProps
import me.chipnesh.presentation.components.RootComponent
import me.chipnesh.presentation.components.rootMapper
import me.chipnesh.presentation.wrappers.hmr.ReloadableApplication
import me.chipnesh.presentation.wrappers.redux.*
import me.chipnesh.presentation.wrappers.redux.Redux.applyMiddleware
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
        val reducers = combine(
                "quota" to ::quoteReducer,
                "user" to ::userReducer,
                "session" to ::sessionReducer
        )
        val middlewares = applyMiddleware<State>(
                thunkMiddleware,
                loggerMiddleware,
                actionTypeChecker
        )
        val store = createStore (
                reducers,
                initState,
                composeWithDevTools(middlewares)
        )

        render(document.getElementById("root")) {
            provider(store) {
                connect<RootComponent, AppProps>(rootMapper)
            }
        }

    }

    override fun dispose(): State = initState
}