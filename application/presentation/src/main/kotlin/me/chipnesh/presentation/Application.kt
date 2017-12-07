package me.chipnesh.presentation

import kotlinext.js.asJsObject
import me.chipnesh.presentation.Session.Companion.EMPTY
import me.chipnesh.presentation.User.Companion.ANON
import me.chipnesh.presentation.components.Action
import me.chipnesh.presentation.components.Root
import me.chipnesh.presentation.wrappers.hmr.ReloadableApplication
import me.chipnesh.presentation.wrappers.react.redux.*
import me.chipnesh.presentation.wrappers.route.*
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
) {
}

lateinit var store: Store<State>

class Application : ReloadableApplication<State>() {
    override val initState: State = State()

    override fun start(state: State) {
        val reducers = js("({})")
        reducers.root = { s: State?, action: Action -> s?.rootReducer(action) ?: initState }
        reducers.routing = ::routerReducer
        store = createStore(
                combineReducers<State, Action>(reducers),
                initState,
                applyMiddleware(
                        thunkMiddleware,
                        loggerMiddleware
                ))
        val history = syncHistoryWithStore(createBrowserHistory(), store)

        render(document.getElementById("root")) {
            provider(store) {
                router(history) {
                    route("/", connect<State, Root>())
                }
            }
        }
    }

    override fun dispose(): State = store.getState()
}