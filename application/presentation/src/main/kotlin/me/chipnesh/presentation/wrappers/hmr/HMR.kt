package me.chipnesh.presentation.wrappers.hmr

external val module: Module

external interface Module {
    val hot: Hot?
}

external interface Hot {
    val data: dynamic

    fun accept()
    fun accept(dependency: String, callback: () -> Unit)
    fun accept(dependencies: Array<String>, callback: (updated: Array<String>) -> Unit)

    fun dispose(callback: (data: dynamic) -> Unit)
}