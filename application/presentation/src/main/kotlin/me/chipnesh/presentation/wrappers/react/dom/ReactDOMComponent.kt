package me.chipnesh.presentation.wrappers.react.dom

import me.chipnesh.presentation.wrappers.react.*
import me.chipnesh.presentation.wrappers.react.RProps
import me.chipnesh.presentation.wrappers.react.RState
import org.w3c.dom.Element

abstract class ReactDOMComponent<P : RProps, S : RState> : ReactComponent<P, S>() {
    abstract fun ReactDOMBuilder.render()

    open fun ReactBuilder.children() {
        children.addAll(ReactWrapper.normalize(props.children))
    }

    val DOMNode: Element
        get() = ReactDOM.findDOMNode(this)

    override fun render() = buildElement { render() }
}

abstract class ReactDOMStatelessComponent<P : RProps> : ReactDOMComponent<P, ReactComponentNoState>() {
    init {
        state = ReactComponentNoState()
    }
}

abstract class ReactDOMPropslessComponent<S : RState> : ReactDOMComponent<ReactComponentNoProps, S>()

abstract class ReactDOMStaticComponent : ReactDOMComponent<ReactComponentNoProps, ReactComponentNoState>() {
    init {
        state = ReactComponentNoState()
    }
}

