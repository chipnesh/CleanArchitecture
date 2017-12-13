package me.chipnesh.presentation.wrappers.js

import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.events.Event
import kotlin.reflect.KClass

external fun require(module: String): dynamic

internal val Event.inputValue: String
    get() = (target as? HTMLInputElement)?.value ?: (target as? HTMLTextAreaElement)?.value ?: ""

internal val Event.enterPressed: Boolean
    get() = this.asDynamic()?.keyCode == 13

fun <T : Any> KClass<T>.createInstance(): T {
    @Suppress("UNUSED_VARIABLE")
    val ctor = this.js

    return js("new ctor()")
}