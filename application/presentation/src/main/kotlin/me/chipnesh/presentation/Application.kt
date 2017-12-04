package me.chipnesh.presentation

import me.chipnesh.presentation.Session.Companion.EMPTY
import me.chipnesh.presentation.User.Companion.ANON
import me.chipnesh.presentation.common.async.async
import me.chipnesh.presentation.common.hmr.ReloadableApplication
import me.chipnesh.presentation.common.redux.*
import me.chipnesh.presentation.common.redux.ReduxNative.Store
import me.chipnesh.presentation.common.redux.ReduxNative.combineReducers
import me.chipnesh.presentation.common.route.connectedRouter
import me.chipnesh.presentation.common.route.route
import me.chipnesh.presentation.components.Action
import me.chipnesh.presentation.components.Root
import react.dom.div
import react.dom.render
import kotlin.browser.document
import kotlin.js.Promise

data class State(
        val quote: String = "",
        val user: User = ANON,
        val session: Session = EMPTY
)

fun State.rootReducer(action: Action): Promise<State> = async {
    val quote = quote.QuoteReducer(action)
    val user = user.UserReducer(action)
    val session = session.SessionReducer(action)
    State(quote, user, session)
}


lateinit var store: Store<State>

class Application : ReloadableApplication<State>() {
    override val initState: State = State()

    override suspend fun start(state: State) {
        store = createStore<State, Action>(state, reducers(), midllewares())

        store.dispatch(thunk<State> {
            dispatch(Action.GetQuote("cat"))
        })

        render(document.getElementById("root")) {
            provider(store) {
                connectedRouter(history) {
                    div {
                        route("/text", Root::class)
                    }
                }
            }
        }
    }

    private fun midllewares(): List<dynamic> {
        return listOf(
                logger<State>(),
                promise<State>(),
                thunkMiddleware,
                routerMiddleware
        )
    }

    private fun <T> reducers() : T {
        return combineReducers<State, Action>(arrayOf(
                { rootReducer(it) },
                routerReducer
        ))
    }

    override fun dispose(): State = store.getState()
}