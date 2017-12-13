package me.chipnesh.web.index

import kotlinext.js.assign
import kotlinx.html.js.onKeyDownFunction
import me.chipnesh.api.AccountInfoResult
import me.chipnesh.api.AuthenticationResult
import me.chipnesh.web.State
import me.chipnesh.web.quote.getRandomQuote
import me.chipnesh.web.wrappers.js.enterPressed
import me.chipnesh.web.wrappers.js.inputValue
import me.chipnesh.web.wrappers.redux.ReactRedux.connect
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.textArea
import rmwc.TypographyType.BODY1
import rmwc.TypographyType.TITLE
import rmwc.typography

sealed class Action {
    data class Login(val result: AuthenticationResult) : Action()
    data class GetQuota(val quota: String) : Action()
    data class GetUser(val result: AccountInfoResult) : Action()
}

interface IndexProps : RProps {
    var quota: String
    var onGetQuote: (String) -> Unit
}

val rootMapper = connect<IndexProps, State>(
        { state, props ->
            assign(props) {
                quota = state.quota
            }
        },
        { dispatch, props ->
            assign(props) {
                onGetQuote = { word: String ->
                    dispatch(getRandomQuote(word))
                }
            }
        })

class IndexComponent : RComponent<IndexProps, RState>() {

    override fun RBuilder.render() {
        div {
            typography(TITLE) { +"Chuck Norris joke:" }
        }
        div {
            typography(BODY1) { +props.quota }
        }
        div {
            textArea {
                attrs {
                    onKeyDownFunction = { event ->
                        if(event.enterPressed) {
                            event.preventDefault()
                            props.onGetQuote(event.inputValue)
                        }
                    }
                }
            }
        }
    }
}