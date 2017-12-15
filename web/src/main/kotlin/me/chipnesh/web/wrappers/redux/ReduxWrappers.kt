package me.chipnesh.web.wrappers.redux

import kotlinext.js.js
import me.chipnesh.web.wrappers.redux.Redux.combineReducers
import react.*

typealias Reducer<S, A> = (S, A) -> S
typealias Enhancer<S> = (dynamic) -> S

@JsModule("redux")
@JsNonModule
external object Redux {

    interface Store<out S : Any> {
        @JsName("getState")
        fun getState(): S

        @JsName("dispatch")
        fun execute(action: Any): Any

        @JsName("subscribe")
        fun subscribe(block: dynamic): dynamic

        @JsName("replaceReducer")
        fun replaceReducer(router:dynamic)
    }

    @JsName("createStore")
    fun <S : Any, A : Any> createStoreInner(reducer: Reducer<*, A>,
                                            initialState: S,
                                            enhancer: Enhancer<S> = definedExternally): Store<S>

    @JsName("combineReducers")
    fun combineReducers(vararg reducers: dynamic): dynamic

    @JsName("applyMiddleware")
    fun <S> applyMiddleware(vararg middleware: () -> (dynamic) -> dynamic): ((dynamic) -> Unit, () -> S) -> Unit

    @JsName("compose")
    fun compose(vararg funcs: dynamic): (dynamic) -> dynamic
}

fun <A : Any> combine(vararg pairs: Pair<String, Reducer<*, A>>): Reducer<Any, A> {
    val reducersObject = js {}
    pairs.forEach { (k, v) -> reducersObject[k] = v }
    return combineReducers(reducersObject)
}

@JsModule("react-redux")
@JsNonModule
external object ReactRedux {

    interface ProviderProps : RProps {
        var store: Redux.Store<*>
    }

    @JsName("Provider")
    class ProviderComponent : React.Component<ProviderProps, RState> {
        override fun render(): ReactElement?
    }

    @JsName("connect")
    fun <P : RProps, S : Any> connect(
            mapStateToProps: ((S, P) -> P)? = definedExternally,
            mapDispatchToProps: (((Any) -> Unit, P) -> P)? = definedExternally
    ): (RClass<P>) -> ReactElement
}

