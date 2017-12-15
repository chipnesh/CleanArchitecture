package me.chipnesh.web.index

import kotlinext.js.assign
import me.chipnesh.web.State
import me.chipnesh.web.wrappers.redux.ReactRedux.connect
import me.chipnesh.web.wrappers.router.ReactRouterRedux.navigate
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.h1

interface IndexProps : RProps {
    var goToQuotaPage: () -> Unit
}

val indexMapper = connect<IndexProps, State>(
        null,
        { execute, props ->
            assign(props) {
                goToQuotaPage = {
                    execute(navigate("/quote"))
                }
            }
        })

class IndexPage : RComponent<IndexProps, RState>() {

    override fun RBuilder.render() {
        div {
            h1 { +"It's main page" }
            props.children()
        }
    }
}