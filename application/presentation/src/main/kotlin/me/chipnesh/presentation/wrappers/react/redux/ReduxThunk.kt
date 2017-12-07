package me.chipnesh.presentation.wrappers.react.redux

import react.*
import kotlin.js.js

@JsModule("redux-thunk")
external object ReduxThunk {
    val default: dynamic
}

@JsModule("redux-logger")
external object Logger {
    fun createLogger(): dynamic
}

val thunkMiddleware = ReduxThunk.default
val loggerMiddleware = Logger.createLogger()

fun <S> thunk(f: Store<S>.() -> Any) = { mid : MiddlewareAPI<S> ->
    val store = object : Store<S> {
        override fun getState(): S  = mid.getState()

        override var dispatch = mid.dispatch

        override fun subscribe(listener: () -> Unit): Unsubscribe = mid.subscribe(listener)

        override fun replaceReducer(nextReducer: (state: S, action: AnyAction) -> S) = mid.replaceReducer(nextReducer)
    }
    store.f()
}

inline fun <S, reified R : RComponent<*, *>> connect(): RClass<*> {
    val name = R::class
    return con<S>()<DispatchProp<S>>(js("name.jClass"))
}

fun <S: Any> RBuilder.provider(store: Store<S>, handler: RHandler<RProps>) = child(Provider::class) {
    attrs {
        this.store = store.unsafeCast<Store<Any>?>()
    }
    handler()
}