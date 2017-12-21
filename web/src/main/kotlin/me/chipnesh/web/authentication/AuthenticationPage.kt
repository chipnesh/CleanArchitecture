package me.chipnesh.web.authentication

import kotlinext.js.assign
import me.chipnesh.web.State
import me.chipnesh.web.wrappers.js.inputValue
import me.chipnesh.web.wrappers.material.raisedButton
import me.chipnesh.web.wrappers.material.textField
import me.chipnesh.web.wrappers.redux.ReactRedux
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.h1

interface AuthenticationProps : RProps {
    var login: (String, String) -> Unit
}

val authenticationMapper = ReactRedux.connect<AuthenticationProps, State>(
        { state, props ->
            assign(props) {
            }
        },
        { execute, props ->
            assign(props) {
                login = { login, password ->
                    execute(loginUser(login, password))
                }
            }
        })

interface ApplicationState : RState {
    var login: String
    var password: String
    var error: String
}

class AuthenticationPage : RComponent<AuthenticationProps, ApplicationState>() {

    override fun RBuilder.render() {
        div {
            h1 { +"Login:" }
            div {
                textField("login") { event ->
                    state.login = event.inputValue
                }
            }
            div {
                textField("password") { event ->
                    state.password = event.inputValue
                }
            }
            div {
                raisedButton("Login") {
                    props.login(state.login, state.password)
                }
            }
        }
    }
}