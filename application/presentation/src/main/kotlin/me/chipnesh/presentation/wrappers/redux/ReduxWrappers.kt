package me.chipnesh.presentation.wrappers.redux

external class Store<out S : Any> {
    @JsName("getState")
    fun getState(): S

    @JsName("dispatch")
    fun doDispatch(action: dynamic)
}

@JsModule("redux")
@JsNonModule
external object Redux {
    @JsName("createStore")
    fun <S: Any> createStore(reducer: (S, dynamic) -> S,
                                      initialState: S,
                                      enhancer: (dynamic) -> S = definedExternally)
        : Store<S>

    @JsName("applyMiddleware")
    fun <S> applyMiddleware(vararg middleware: () -> (dynamic) -> dynamic)
        : ((dynamic) -> Unit, () -> S) -> Unit

    @JsName("compose")
    fun compose(vararg funcs: dynamic): (dynamic) -> dynamic
}


fun <S: Any> Store<S>.dispatch(action: ReduxAction) {
    this.doDispatch(action())
}
