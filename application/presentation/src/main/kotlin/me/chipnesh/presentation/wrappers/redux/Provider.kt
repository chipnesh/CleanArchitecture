package me.chipnesh.presentation.wrappers.redux

import me.chipnesh.presentation.wrappers.js.require
import me.chipnesh.presentation.wrappers.react.RProps
import me.chipnesh.presentation.wrappers.react.ReactExternalComponentSpec


val Provider: dynamic = require("react-redux").Provider

class ReactProviderProps(var store: Any) : RProps()

object ProviderComponent : ReactExternalComponentSpec<ReactProviderProps>(Provider)
