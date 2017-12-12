package me.chipnesh.presentation.wrappers.react.dom

import org.w3c.dom.Element
import me.chipnesh.presentation.wrappers.react.RProps
import me.chipnesh.presentation.wrappers.react.RState
import me.chipnesh.presentation.wrappers.react.ReactComponent
import me.chipnesh.presentation.wrappers.react.ReactElement

@JsModule("react-dom")
external object ReactDOM {
    fun render(element: ReactElement?, container: Element?)
    fun<P: RProps, S: RState> findDOMNode(component: ReactComponent<P, S>): Element
    fun unmountComponentAtNode(domContainerNode: Element?)
}

fun ReactDOM.render(container: Element?, handler: ReactDOMBuilder.() -> Unit) =
    render(buildElement(handler), container)
