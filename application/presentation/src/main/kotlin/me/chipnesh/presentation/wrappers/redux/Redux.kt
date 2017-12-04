package me.chipnesh.presentation.wrappers.redux

import kotlinext.js.assign
import me.chipnesh.presentation.State
import me.chipnesh.presentation.wrappers.redux.ReactReduxNative.connectRedux
import me.chipnesh.presentation.wrappers.redux.ReduxNative.Store
import me.chipnesh.presentation.wrappers.redux.ReduxNative.applyMiddleware
import me.chipnesh.presentation.wrappers.redux.ReduxNative.compose
import me.chipnesh.presentation.wrappers.redux.ReduxNative.createStore
import me.chipnesh.presentation.wrappers.route.RouterReduxNative.routerReducer
import me.chipnesh.presentation.wrappers.route.createBrowserHistory
import me.chipnesh.presentation.wrappers.route.routerMiddleware
import react.*
import kotlin.reflect.KClass

typealias Middleware<S> = Store<S>.() -> ((Any) -> Any) -> (Any) -> Any
typealias Dispatch = (Any) -> Any
typealias GetState<S> = () -> S

val history = createBrowserHistory()
val thunkMiddleware: Middleware<*> = ReduxThunk.default
val routerMiddleware: Middleware<*> = routerMiddleware(history)
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
    val native = createStore(
            wrapper,
            init,
            compose(applyMiddleware(*(middleWares + actionTypeChecker()).toTypedArray()))
    )
    return native.unsafeCast<Store<S>>()
}

fun <S> promise(): Middleware<S> = {
    { next ->
        { action ->
            if (jsTypeOf(action) === "function") {
                action.asDynamic()(next)
            } else {
                next(action)
            }
        }
    }
}

fun <S> logger(): Middleware<S> = {
    { next ->
        { action ->
            console.log("Action:", action::class.simpleName)
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

fun <P : RProps> connect(component: React.Component<*, *>, state: P.(State) -> Unit)
        : KClass<out React.Component<*, *>> {
    return connectRedux({ assign(it, state) })(component.asDynamic().constructor)::class
}