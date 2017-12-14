package me.chipnesh.web.wrappers.redux

import kotlinext.js.jsObject
import me.chipnesh.web.wrappers.redux.ReactRedux.ProviderComponent
import me.chipnesh.web.wrappers.redux.Redux.Store
import me.chipnesh.web.wrappers.redux.Redux.createStoreInner
import me.chipnesh.web.wrappers.router.ReactRouterDom.withRouter
import react.*

inline fun <reified T : React.Component<out RProps, *>> RBuilder.connect(
        crossinline connectFunction: (RClass<RProps>) -> ReactElement,
        noinline handler: RHandler<out RProps> = {}
): ReactElement {
    val type = T::class.js.unsafeCast<RClass<RProps>>()
    return withRouter(child(connectFunction(type), jsObject {}, handler))
}

inline fun <reified S : Any, reified A : Any> createStore(noinline reducer: Reducer<Any, A>,
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

fun <S : Any> thunk(f: Store<S>.() -> Any) = { dispatch: (Any) -> Any,
                                               getState: () -> S,
                                               subscribe: (dynamic) -> dynamic,
                                               replaceReducer: (dynamic) -> dynamic ->
    val store = object : Store<S> {
        override fun replaceReducer(router: dynamic) = replaceReducer(router)
        override fun subscribe(block: dynamic) = subscribe(block)
        override fun dispatch(action: dynamic) = dispatch(action)
        override fun getState() = getState()
    }
    store.f()
}

fun <S : Any> RBuilder.reduxProvider(store: Store<S>, handler: RHandler<RProps>) =
        child(ProviderComponent::class) {
            attrs {
                this.store = store
            }
            handler()
        }