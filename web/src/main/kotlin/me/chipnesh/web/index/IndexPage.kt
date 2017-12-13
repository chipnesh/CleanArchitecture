package me.chipnesh.web.index

import kotlinext.js.assign
import kotlinx.html.js.onClickFunction
import me.chipnesh.web.State
import me.chipnesh.web.wrappers.redux.ReactRedux.connect
import me.chipnesh.web.wrappers.router.ReactRouterRedux.push
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.button
import react.dom.div
import rmwc.TypographyType.TITLE
import rmwc.typography

interface IndexProps : RProps {
    var goToQuotaPage: () -> Unit
}

val rootMapper = connect<IndexProps, State>(
        null,
        { dispatch, props ->
            assign(props) {
                goToQuotaPage = {
                    dispatch(push("/quota"))
                }
            }
        })

class IndexComponent : RComponent<IndexProps, RState>() {

    override fun RBuilder.render() {
        div {
            typography(TITLE) { +"Hello" }
        }

        button {
            +"Go to quota page"
            attrs {
                onClickFunction = {
                    props.goToQuotaPage()
                }
            }
        }
    }
}