package me.chipnesh.web.wrappers.material

import me.chipnesh.web.wrappers.material.MaterialUI.RaisedButton
import org.w3c.dom.events.Event
import react.RBuilder
import react.RHandler
import react.RProps
import react.ReactElement

fun RBuilder.materialUi(handler: RHandler<RProps>) = child(Styles.MuiThemeProvider::class, handler)
fun RBuilder.raisedButton(label: String, onClick: (Event) -> Unit): ReactElement {
    return child(RaisedButton::class) {
        attrs {
            this.label = label
            this.onClick = onClick
        }
    }
}