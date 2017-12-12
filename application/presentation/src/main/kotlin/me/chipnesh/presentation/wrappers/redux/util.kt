package me.chipnesh.presentation.wrappers.redux

import me.chipnesh.presentation.wrappers.js.createInstance
import me.chipnesh.presentation.wrappers.react.*
import me.chipnesh.presentation.wrappers.react.RProps
import me.chipnesh.presentation.wrappers.react.RState
import me.chipnesh.presentation.wrappers.react.React
import me.chipnesh.presentation.wrappers.react.ReactElement


inline fun <reified T : ReactComponent<P, S>, reified P : RProps, S : RState>
        ReactComponentSpec<T, P, S>.asConnectedComponent(
        connectFunction: (Any) -> ReactElement, props: P = P::class.createInstance()): Any {
    val wrap = ReactComponent.wrap(T::class)
    return React.createElement(
        connectFunction(ReactBuilder.Node(wrap, props).type), null)
}
