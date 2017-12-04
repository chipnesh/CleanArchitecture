package me.chipnesh.presentation.components

import me.chipnesh.presentation.common.route.RouteResultProps
import me.chipnesh.presentation.store
import react.*
import rmwc.TypographyType.CAPTION
import rmwc.TypographyType.TITLE
import rmwc.button
import rmwc.typography


sealed class Action {
    data class Login(val login: String, val password: String): Action()
    data class GetQuote(val word: String): Action()
    data class GetUser(val login: String): Action()
    data class QuoteLoaded(val quote: String): Action()
}

interface AppProps : RProps {
    var text: String
}

interface AppState : RState {
    var word: String
    var quote: String
}

class Root : RComponent<RouteResultProps<AppProps>, AppState>() {

    override fun AppState.init(props: RouteResultProps<AppProps>) {
        setState {
            word = props.match.params.text
        }
    }

    override fun RBuilder.render() {
        typography(TITLE) {
            +state.word
        }
        typography(CAPTION) {
            +state.quote
        }

        button {
            +"Get quote"
            attrs {
                onClick = {
                    store.dispatch(Action.GetQuote(state.word))
                }
            }
        }
    }

}