@file:JsModule("react-redux")
package me.chipnesh.presentation.wrappers.react.redux

import react.*
import kotlin.js.*

external interface DispatchProp<S> : RProps {
    var dispatch: Dispatch<S>
}
external interface AdvancedComponentDecorator<TProps : RProps, TOwnProps: RProps> {
    @nativeInvoke
    operator fun invoke(component: RComponent<TProps, *>): RClass<TOwnProps>
}
external interface InferableComponentEnhancerWithProps<TInjectedProps : RProps, TNeedsProps: RProps> {
    @nativeInvoke
    operator fun <P : TInjectedProps> invoke(component: RComponent<P, *>): RClass<P>
}
external interface Connect {
    @nativeInvoke
    operator fun <S> invoke(): InferableComponentEnhancerWithProps<DispatchProp<S>, RProps>
}
@JsName("connect")
external var con: Connect = definedExternally
external interface MapStateToProps<TStateProps, TOwnProps, State> {
    @nativeInvoke
    operator fun invoke(state: State, ownProps: TOwnProps): TStateProps
}
external interface MapStateToPropsFactory<TStateProps, TOwnProps, State> {
    @nativeInvoke
    operator fun invoke(initialState: State, ownProps: TOwnProps): MapStateToProps<TStateProps, TOwnProps, State>
}
external interface MapDispatchToPropsFunction<TDispatchProps, TOwnProps> {
    @nativeInvoke
    operator fun invoke(dispatch: Dispatch<Any>, ownProps: TOwnProps): TDispatchProps
}
external interface MapDispatchToPropsFactory<TDispatchProps, TOwnProps> {
    @nativeInvoke
    operator fun invoke(dispatch: Dispatch<Any>, ownProps: TOwnProps): dynamic /* TDispatchProps | MapDispatchToPropsFunction<TDispatchProps, TOwnProps> */
}
external interface MergeProps<TStateProps, TDispatchProps, TOwnProps, TMergedProps> {
    @nativeInvoke
    operator fun invoke(stateProps: TStateProps, dispatchProps: TDispatchProps, ownProps: TOwnProps): TMergedProps
}
external interface Options<State>
external fun <S, TProps, TOwnProps, TFactoryOptions> connectAdvanced(): Unit = definedExternally
external interface SelectorFactory<S, TProps, TOwnProps, TFactoryOptions> {
    @nativeInvoke
    operator fun invoke(dispatch: Dispatch<S>, factoryOptions: TFactoryOptions): Selector<S, TProps, TOwnProps>
}
external interface Selector<S, TProps, TOwnProps> {
    @nativeInvoke
    operator fun invoke(state: S, ownProps: TOwnProps): TProps
}
external interface ConnectOptions {
    var getDisplayName: ((componentName: String) -> String)? get() = definedExternally; set(value) = definedExternally
    var methodName: String? get() = definedExternally; set(value) = definedExternally
    var renderCountProp: String? get() = definedExternally; set(value) = definedExternally
    var shouldHandleStateChanges: Boolean? get() = definedExternally; set(value) = definedExternally
    var storeKey: String? get() = definedExternally; set(value) = definedExternally
    var withRef: Boolean? get() = definedExternally; set(value) = definedExternally
}

external class Provider : React.Component<ProviderProps, RState> {
    override fun render(): ReactElement?
}
external interface ProviderProps : RProps {
    var store: Store<Any>? get() = definedExternally; set(value) = definedExternally
    var children: RComponent<*, *>? get() = definedExternally; set(value) = definedExternally
}
external fun createProvider(storeKey: String): Any? = definedExternally
