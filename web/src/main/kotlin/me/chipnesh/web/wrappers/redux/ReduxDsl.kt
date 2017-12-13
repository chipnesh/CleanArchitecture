package me.chipnesh.web.wrappers.redux

import me.chipnesh.web.wrappers.js.createInstance
import me.chipnesh.web.wrappers.redux.ReactRedux.ProviderComponent
import me.chipnesh.web.wrappers.redux.Redux.Store
import me.chipnesh.web.wrappers.redux.Redux.createStoreInner
import react.*

inline fun <reified T : RComponent<P, *>, reified P: RProps> RBuilder.connect(
        connectFunction: (RClass<P>) -> ReactElement
) {
    val type = T::class.js.unsafeCast<RClass<P>>()
    val props = P::class.createInstance()
    child(React.createElement(connectFunction(type), props, arrayOf<ReactElement>()))
}

inline fun <reified S : Any, reified A: Any> createStore(noinline reducer: Reducer<Any, A>,
                                                         initialState: S,
                                                         noinline enhancer: Enhancer<S>): Store<S> {
    val actionType = A::class
    val wrapper = { state: S, a: dynamic ->
        if (actionType.isInstance(a["action"].unsafeCast<A>())) {
            val origin = a["action"].unsafeCast<A>()
            reducer(state, origin)
        } else {
            state
        }
    }
    return createStoreInner(wrapper, initialState, enhancer)
}

fun <S : Any> thunk(f: Store<S>.() -> Any) = {
    dispatch: (Any) -> Any,
    getState: () -> S,
    subscribe: (dynamic) ->
    dynamic ->
    val store = object : Store<S> {
        override fun subscribe(block: dynamic) = subscribe(block)
        override fun dispatch(action: dynamic) = dispatch(action)
        override fun getState() = getState()
    }
    store.f()
}

fun <S: Any> RBuilder.provider(store: Store<S>, handler: RHandler<RProps>) =
        child(ProviderComponent::class) {
            attrs {
                this.store = store
            }
            handler()
        }