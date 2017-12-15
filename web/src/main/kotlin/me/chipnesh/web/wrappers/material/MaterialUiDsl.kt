package me.chipnesh.web.wrappers.material

import me.chipnesh.web.wrappers.material.MaterialUI.RaisedButton
import me.chipnesh.web.wrappers.material.MaterialUI.TextField
import org.w3c.dom.events.Event
import react.RBuilder
import react.RHandler
import react.RProps
import react.ReactElement

fun RBuilder.materialUI(handler: RHandler<RProps>) = child(Styles.MuiThemeProvider::class, handler)

fun RBuilder.raisedButton(label: String, onClick: (Event) -> Unit): ReactElement {
    return child(RaisedButton::class) {
        attrs {
            this.label = label
            this.onClick = onClick
        }
    }
}

fun RBuilder.textField(hintText: String, onKeyDown: (Event) -> Unit): ReactElement {
    return child(TextField::class) {
        attrs {
            this.hintText = hintText
            this.onKeyDown = onKeyDown
        }
    }
}