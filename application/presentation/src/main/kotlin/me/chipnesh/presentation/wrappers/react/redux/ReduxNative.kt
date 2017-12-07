@file:JsModule("redux")
package me.chipnesh.presentation.wrappers.react.redux

external interface IAction

external fun <S, A> applyMiddleware(vararg middleware: dynamic):
        (next: (reducer: (state: S, action: A) -> S, preloadedState: S? /*= null*/) -> Store<S>) -> (reducer: (state: S, action: A) -> S, preloadedState: S? /*= null*/) -> Store<S>?
external fun <S, A> combineReducers(vararg middleware: dynamic): (state: S, action: A) -> S

external interface AnyAction : IAction {
    @nativeGetter
    operator fun get(extraProps: String): Any?
    @nativeSetter
    operator fun set(extraProps: String, value: Any)
}
external interface ReducersMapObject<S> {
    @nativeGetter
    operator fun get(key: String): ((state: S, action: AnyAction) -> S)?
    @nativeSetter
    operator fun set(key: String, value: (state: S, action: AnyAction) -> S)
}
external interface Dispatch<S> {
    @nativeInvoke
    operator fun <A> invoke(action: A): A
}

external interface Unsubscribe {
    @nativeInvoke
    operator fun invoke()
}
external interface Store<S> {
    var dispatch: Dispatch<S>
    fun getState(): S
    fun subscribe(listener: () -> Unit): Unsubscribe
    fun replaceReducer(nextReducer: (state: S, action: AnyAction) -> S)
}

@JsName("createStore")
external fun <S, A: IAction> createStore(reducer: (state: S, action: A) -> S,
                                            enhancer: (next: (reducer: (state: S, action: A) -> S, preloadedState: S? /*= null*/) -> Store<S>) -> (reducer: (state: S, action: A) -> S, preloadedState: S? /*= null*/) -> Store<S>? = definedExternally /* null */): Store<S>
@JsName("createStore")
external fun <S, A: IAction> createStore(reducer: (state: S, action: A) -> S,
                                      preloadedState: S,
                                      enhancer: (next: (reducer: (state: S, action: A) -> S, preloadedState: S? /*= null*/) -> Store<S>) -> (reducer: (state: S, action: A) -> S, preloadedState: S? /*= null*/) -> Store<S>? = definedExternally /* null */): Store<S>
external interface MiddlewareAPI<S> {
    var dispatch: Dispatch<S>
    fun getState(): S
    fun subscribe(listener: () -> Unit): Unsubscribe
    fun replaceReducer(nextReducer: (state: S, action: AnyAction) -> S)
}

external interface Middleware {
    @nativeInvoke
    operator fun <S> invoke(api: MiddlewareAPI<S>): (next: Dispatch<S>) -> Dispatch<S>
}
external interface ActionCreator<A> {
    @nativeInvoke
    operator fun invoke(vararg args: Any): A
}
external interface ActionCreatorsMapObject {
    @nativeGetter
    operator fun get(key: String): ActionCreator<Any>?
    @nativeSetter
    operator fun set(key: String, value: ActionCreator<Any>)
}