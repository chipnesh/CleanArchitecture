package me.chipnesh.presentation.components

import me.chipnesh.api.AccountInfoResult
import me.chipnesh.api.AuthenticationResult
import me.chipnesh.presentation.State
import me.chipnesh.presentation.getQuote
import me.chipnesh.presentation.wrappers.react.redux.DispatchProp
import me.chipnesh.presentation.wrappers.route.RouteResultProps
import react.*
import react.dom.div
import react.dom.span
import rmwc.TypographyType.TITLE
import rmwc.button
import rmwc.typography


sealed class Action : me.chipnesh.presentation.wrappers.react.redux.IAction {
    data class Login(val result: AuthenticationResult): Action()
    data class GetQuote(val quote: String): Action()
    data class GetUser(val result: AccountInfoResult): Action()
}

interface AppProps : DispatchProp<State> {
}

interface AppState : RState {
    var quote: String
}

class Root : RComponent<RouteResultProps<State, AppProps>, AppState>() {

    override fun RBuilder.render() {
        div {
            typography(TITLE) {
                span {
                    +("Title is " + state.quote)
                }
            }
            button {
                +"Get quote"
                attrs {
                    onClick = {
                        getQuote("woman")
                    }
                }
            }
        }
    }
}