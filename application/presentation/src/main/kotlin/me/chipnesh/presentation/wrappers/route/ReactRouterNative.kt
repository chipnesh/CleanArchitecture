@file:JsModule("react-router")
package me.chipnesh.presentation.wrappers.route

import react.*

@JsName("Router")
external class RouterComponent : React.Component<RouterProps, RState> {
    override fun render(): ReactElement?
}

external interface RouterProps : RProps {
    var history: dynamic
}