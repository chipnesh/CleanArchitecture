package me.chipnesh.web.quote

import kotlinext.js.assign
import me.chipnesh.web.State
import me.chipnesh.web.wrappers.js.enterPressed
import me.chipnesh.web.wrappers.js.inputValue
import me.chipnesh.web.wrappers.material.raisedButton
import me.chipnesh.web.wrappers.material.textField
import me.chipnesh.web.wrappers.redux.ReactRedux.connect
import me.chipnesh.web.wrappers.router.ReactRouterRedux.goto
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.h1
import react.dom.h2

interface QuotaProps : RProps {
    var quota: String
    var onGetQuote: (String) -> Unit
    var goToIndex: () -> Unit
}

val quotaMapper = connect<QuotaProps, State>(
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
                goToIndex = { dispatch(goto("/")) }
            }
        })

class QuotaPage : RComponent<QuotaProps, RState>() {

    override fun RBuilder.render() {
        div {
            h1 { +"Chuck Norris joke:" }
            div {
                textField("theme") { event ->
                    if (event.enterPressed) {
                        event.preventDefault()
                        props.onGetQuote(event.inputValue)
                    }
                }
            }
            h2 { +props.quota }
            div {
                raisedButton("Go to quota page") {
                    props.goToIndex()
                }
            }
        }
    }
}