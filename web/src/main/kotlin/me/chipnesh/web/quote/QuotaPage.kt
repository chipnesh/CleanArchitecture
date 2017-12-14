package me.chipnesh.web.quote

import kotlinext.js.assign
import kotlinx.html.js.onKeyDownFunction
import me.chipnesh.web.State
import me.chipnesh.web.wrappers.js.enterPressed
import me.chipnesh.web.wrappers.js.inputValue
import me.chipnesh.web.wrappers.redux.ReactRedux.connect
import me.chipnesh.web.wrappers.router.ReactRouterRedux.push
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.br
import react.dom.div
import react.dom.textArea
import rmwc.TypographyType.BODY1
import rmwc.TypographyType.TITLE
import rmwc.button
import rmwc.typography

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
                goToIndex = { dispatch(push("/")) }
            }
        })

class QuotaComponent : RComponent<QuotaProps, RState>() {

    override fun RBuilder.render() {
        div {
            typography(TITLE) { +"Chuck Norris joke:" }
            br {  }
            textArea {
                attrs {
                    onKeyDownFunction = { event ->
                        if (event.enterPressed) {
                            event.preventDefault()
                            props.onGetQuote(event.inputValue)
                        }
                    }
                }
            }
            br {  }
            typography(BODY1) { +props.quota }
            br {  }
            button {
                +"Go to quota page"
                attrs {
                    onClick = {
                        props.goToIndex()
                    }
                }
            }        }
    }
}