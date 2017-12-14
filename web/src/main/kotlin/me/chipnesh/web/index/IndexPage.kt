package me.chipnesh.web.index

import kotlinext.js.assign
import me.chipnesh.web.State
import me.chipnesh.web.wrappers.material.raisedButton
import me.chipnesh.web.wrappers.redux.ReactRedux.connect
import me.chipnesh.web.wrappers.router.ReactRouterRedux.push
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState

interface IndexProps : RProps {
    var goToQuotaPage: () -> Unit
}

val indexMapper = connect<IndexProps, State>(
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
        raisedButton("Go to quota page") {
            props.goToQuotaPage()
        }
    }
}