package me.chipnesh.presentation.components

import me.chipnesh.api.AccountInfoResult
import me.chipnesh.api.AuthenticationResult
import me.chipnesh.presentation.getQuote
import me.chipnesh.presentation.wrappers.route.RouteResultProps
import react.*
import react.dom.div
import rmwc.TypographyType.CAPTION
import rmwc.TypographyType.TITLE
import rmwc.button
import rmwc.typography


sealed class Action {
    data class Login(val result: AuthenticationResult): Action()
    data class GetQuote(val quote: String): Action()
    data class GetUser(val result: AccountInfoResult): Action()
}

interface AppProps : RProps {
    var quote: String
}

interface AppState : RState {
    var word: String
    var quote: String
}

class Root : RComponent<RouteResultProps<AppProps>, AppState>() {

    override fun RBuilder.render() {
        div {
            typography(TITLE) {
                +state.word
            }
        }
        div {
            typography(CAPTION) {
                +state.quote
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