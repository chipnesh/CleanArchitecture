package me.chipnesh.presentation

import me.chipnesh.presentation.Session.Companion.EMPTY
import me.chipnesh.presentation.User.Companion.ANON
import me.chipnesh.presentation.components.Action
import me.chipnesh.presentation.components.routerComponent
import me.chipnesh.presentation.wrappers.hmr.ReloadableApplication
import me.chipnesh.presentation.wrappers.react.dom.ReactDOM
import me.chipnesh.presentation.wrappers.react.dom.render
import me.chipnesh.presentation.wrappers.redux.*
import me.chipnesh.presentation.wrappers.redux.Redux.applyMiddleware
import me.chipnesh.presentation.wrappers.redux.Redux.createStore
import kotlin.browser.document

data class State(
        val quote: String = "",
        val user: User = ANON,
        val session: Session = EMPTY
)

fun rootReducer(state: State, action: Action) = State(
        quote = state.quote.QuoteReducer(action),
        user = state.user.UserReducer(action),
        session = state.session.SessionReducer(action)
)

lateinit var store: Store<State>

class Application : ReloadableApplication<State>() {
    override val initState: State = State()

    override fun start(state: State) {
        val reduxStore = createStore(::rootReducer, State(),
                composeWithDevTools(applyMiddleware<State>(ReduxThunk, loggerMiddleware))
        )
        ReactDOM.render(document.getElementById("root")) {
            ProviderComponent {
                store = reduxStore
                children = asConnectedElement(routerComponent)
            }

        }
    }

    override fun dispose(): State = store.getState()
}