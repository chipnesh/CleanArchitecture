@file:JsModule("react-redux")
package me.chipnesh.presentation.wrappers.redux

import me.chipnesh.presentation.wrappers.react.RProps
import me.chipnesh.presentation.wrappers.react.ReactElement


@JsName("connect")
external fun <P : RProps, S: Any> connect(
    mapStateToProps: ((S, P) -> P)? = definedExternally,
    mapDispatchToProps: (((dynamic) -> Unit, P) -> P)? = definedExternally
): (Any) -> ReactElement

