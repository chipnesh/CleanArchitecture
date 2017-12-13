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

interface IndexProps : RProps {
    var goToQuotaPage: () -> Unit
}

val rootMapper = connect<IndexProps, State>(
        { state, props ->
            assign(props) {}
        },
        { dispatch, props ->
            assign(props) {
                goToQuotaPage = {
                    dispatch(push("/quota"))
                }
            }
        })

class IndexComponent : RComponent<IndexProps, RState>() {

    override fun RBuilder.render() {
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