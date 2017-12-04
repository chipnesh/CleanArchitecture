package me.chipnesh.presentation.common.redux

import kotlinext.js.asJsObject
import kotlinext.js.js
import me.chipnesh.presentation.common.async.launch
import me.chipnesh.presentation.common.redux.ReduxNative.Store
import me.chipnesh.presentation.common.route.BrowserHistoryNative
import me.chipnesh.presentation.common.route.RouterReduxNative.routerMiddleware
import me.chipnesh.presentation.common.route.RouterReduxNative.routerReducer
import react.RBuilder
import react.RHandler
import react.RProps
import kotlin.js.Promise
import kotlin.reflect.KClass

typealias Middleware<S> = Store<S>.() -> ((Any) -> Any) -> (Any) -> Any
typealias Dispatch = (Any) -> Any
typealias GetState<S> = () -> S

val history = BrowserHistoryNative.default
val thunkMiddleware: dynamic = ReduxThunk.default
val routerMiddleware: dynamic = routerMiddleware(history)
val routerReducer: dynamic = routerReducer()

fun <S> thunk(f: Store<S>.() -> Any) = { dispatch: Dispatch, getState: GetState<S> ->
    val store = object : Store<S> {
        override fun dispatch(action: Any): Any = dispatch(action)
        override fun getState(): S = getState()
    }
    store.f()
}

inline fun <S, reified A : Any> createStore(
        init: S,
        noinline reducer: S.(A) -> S,
        middleWares: List<Middleware<S>> = emptyList()): Store<S> {
    return createStoreInner(A::class, init, reducer, middleWares)
}

fun <S, A : Any> createStoreInner(
        actionType: KClass<A>,
        init: S,
        reducer: S.(A) -> S,
        middleWares: List<Middleware<S>> = emptyList()
): Store<S> {
    val wrapper = { state: S, a: dynamic ->
        if (actionType.isInstance(a["action"].unsafeCast<A>())) {
            val origin = a["action"].unsafeCast<A>()
            state.reducer(origin)
        } else {
            state
        }
    }
    val native = ReduxNative.createStore(
            wrapper,
            init,
            ReduxNative.applyMiddleware(*(middleWares + actionTypeChecker()).toTypedArray())
    )
    return object : Store<S> by native {}
}

fun <S> promise(): Middleware<S> = {
    { next ->
        { action ->
            if (jsTypeOf(action) === "function") {
                (action as Promise<*>).then {
                    next(action)
                }
            } else {
                next(action)
            }
        }
    }
}

fun <S> logger(): Middleware<S> = {
    { next ->
        { action ->
            console.log("Action:", action)
            console.log("Before:", getState())
            val result = next(action)
            console.log("After:", getState())
            result
        }
    }
}

private fun <S> actionTypeChecker(): Middleware<S> = {
    { next ->
        { action ->
            val actionWrapper = js("({})")
            actionWrapper["action"] = action
            actionWrapper["type"] = action::class.simpleName
            next(actionWrapper as Any)
        }
    }
}

fun <S : Any> RBuilder.provider(store: Store<S>, handler: RHandler<RProps>) =
        child(ReactReduxNative.ProviderComponent::class) {
            attrs {
                this.store = store
            }
            this.handler()
        }
