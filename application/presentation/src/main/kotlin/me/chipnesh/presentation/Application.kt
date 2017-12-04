package me.chipnesh.presentation

import me.chipnesh.presentation.Session.Companion.EMPTY
import me.chipnesh.presentation.User.Companion.ANON
import me.chipnesh.presentation.components.Action
import me.chipnesh.presentation.components.Root
import me.chipnesh.presentation.wrappers.hmr.ReloadableApplication
import me.chipnesh.presentation.wrappers.redux.*
import me.chipnesh.presentation.wrappers.redux.ReduxNative.Store
import me.chipnesh.presentation.wrappers.redux.ReduxNative.combineReducers
import me.chipnesh.presentation.wrappers.route.connectRouter
import me.chipnesh.presentation.wrappers.route.route
import me.chipnesh.presentation.wrappers.route.router
import me.chipnesh.presentation.wrappers.route.switch
import react.dom.render
import kotlin.browser.document

data class State(
        val quote: String = "",
        val user: User = ANON,
        val session: Session = EMPTY
)

fun State.rootReducer(action: Action) = State(
        quote = quote.QuoteReducer(action),
        user = user.UserReducer(action),
        session = session.SessionReducer(action)
)

lateinit var store: Store<State>

class Application : ReloadableApplication<State>() {
    override val initState: State = State()

    override fun start(state: State) {
        val rootReducer = combineReducers(State::rootReducer, routerReducer)
        store = createStore<State, Action>(
                state,
                connectRouter(history)(rootReducer),
                midllewares()
        )

        render(document.getElementById("root")) {
            router {
                switch {
                    route("/", Root::class)
                }
            }
        }
    }

    private fun midllewares(): List<Middleware<dynamic>> {
        return listOf(
                routerMiddleware,
                promise<State>(),
                logger<State>(),
                thunkMiddleware
        )
    }

    override fun dispose(): State = store.getState()
}