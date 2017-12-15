package me.chipnesh.web.quote

import kotlinext.js.assign
import me.chipnesh.web.State
import me.chipnesh.web.wrappers.js.enterPressed
import me.chipnesh.web.wrappers.js.inputValue
import me.chipnesh.web.wrappers.material.raisedButton
import me.chipnesh.web.wrappers.material.textField
import me.chipnesh.web.wrappers.redux.ReactRedux.connect
import me.chipnesh.web.wrappers.router.ReactRouterRedux.navigate
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.h1
import react.dom.h2

interface QuotaProps : RProps {
    var quote: String
    var onGetQuote: (String) -> Unit
    var goToIndex: () -> Unit
}

val quotaMapper = connect<QuotaProps, State>(
        { state, props ->
            assign(props) {
                quote = state.quote
            }
        },
        { execute, props ->
            assign(props) {
                onGetQuote = { word: String ->
                    execute(getRandomQuote(word))
                }
                goToIndex = { execute(navigate("/")) }
            }
        })

class QuotaPage : RComponent<QuotaProps, RState>() {

    override fun RBuilder.render() {
        div {
            h1 { +"Chuck Norris quote:" }
            div {
                textField("theme") { event ->
                    if (event.enterPressed) {
                        event.preventDefault()
                        props.onGetQuote(event.inputValue)
                    }
                }
            }
            h2 { +props.quote }
            div {
                raisedButton("Go to main page") {
                    props.goToIndex()
                }
            }
        }
    }
}