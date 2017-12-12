package me.chipnesh.presentation.components

import kotlinx.html.div
import kotlinx.html.label
import me.chipnesh.api.AccountInfoResult
import me.chipnesh.api.AuthenticationResult
import me.chipnesh.presentation.State
import me.chipnesh.presentation.components.RouterComponent.RouterProps
import me.chipnesh.presentation.wrappers.js.jsObject
import me.chipnesh.presentation.wrappers.react.RProps
import me.chipnesh.presentation.wrappers.react.ReactComponentStatelessSpec
import me.chipnesh.presentation.wrappers.react.dom.ReactDOMBuilder
import me.chipnesh.presentation.wrappers.react.dom.ReactDOMStatelessComponent
import me.chipnesh.presentation.wrappers.redux.ReduxAction
import me.chipnesh.presentation.wrappers.redux.connect


sealed class Action(payload: Any) : ReduxAction(payload) {
    data class Login(val result: AuthenticationResult) : Action(result)
    data class GetQuote(val quote: String) : Action(quote)
    data class GetUser(val result: AccountInfoResult) : Action(result)
}

//https://github.com/Xantier/fullstack-kotlin/blob/master/frontend/src/main/kotlin/com/packtpub/components/ProjectList.kt

val routerComponent = connect<RouterProps, State>({ state: State, _ ->
    jsObject {
        quota = state.quote
    }
}, { dispatch, _ ->
    jsObject {
        getQuote = { target, value ->
            dispatch(Action.GetQuote("women"))
        }
    }
})

class RouterComponent : ReactDOMStatelessComponent<RouterProps>() {
    companion object : ReactComponentStatelessSpec<RouterComponent, RouterProps>


    override fun ReactDOMBuilder.render() {
        div {
            label {
                +"Text"
            }
        }
    }

    class RouterProps(
            var quota: String = "",
            var getQuote: (Any, String) -> Unit
    ) : RProps()
}