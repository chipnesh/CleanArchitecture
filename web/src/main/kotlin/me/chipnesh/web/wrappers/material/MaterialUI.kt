package me.chipnesh.web.wrappers.material

import org.w3c.dom.events.Event
import react.RProps
import react.RState
import react.React
import react.ReactElement

@JsModule("material-ui/styles")
external object Styles {
    @JsName("MuiThemeProvider")
    class MuiThemeProvider : React.Component<RProps, RState> {
        override fun render(): ReactElement?
    }
}

@JsModule("material-ui")
@JsNonModule
external object MaterialUI {

    @JsName("RaisedButton")
    class RaisedButton : React.Component<RaisedButtonProps, RState> {
        override fun render(): ReactElement?
    }

    interface RaisedButtonProps : RProps {
        var label: String
        var onClick: (Event) -> Unit
    }

    @JsName("TextField")
    class TextField : React.Component<TextFieldProps, RState> {
        override fun render(): ReactElement?
    }

    interface TextFieldProps : RProps {
        var errorText: String
        var hintText: String
        var type: String
        var onKeyDown: (Event) -> Unit
    }
}