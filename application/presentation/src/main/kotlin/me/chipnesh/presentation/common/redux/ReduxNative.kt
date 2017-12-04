package me.chipnesh.presentation.common.redux

import react.RProps
import react.RState
import react.React
import react.ReactElement

@JsModule("redux")
external object ReduxNative {
    fun <S> createStore(reducer: (S, dynamic) -> S, initialState: S = definedExternally, enhancer: dynamic = definedExternally): Store<S>

    interface Store<out S> {
        fun getState(): S
        fun dispatch(action: Any): Any
    }

    fun applyMiddleware(vararg middleware: dynamic): dynamic
    fun <S, A : Any> combineReducers(reducers: Array<S.(A) -> Any>): dynamic
}

@JsModule("redux-devtools")
external object DevUtils {
    fun devTools(): dynamic
}


@JsModule("react-redux")
external object ReactReduxNative {

    fun connect(vararg middleware: dynamic): dynamic

    @JsName("Provider")
    class ProviderComponent : React.Component<ProviderProps<Any>, RState> {
        override fun render(): ReactElement?
    }

    interface ProviderProps<S> : RProps {
        var store: ReduxNative.Store<S>
    }
}