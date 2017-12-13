package me.chipnesh.web.wrappers.hmr

import kotlin.browser.document

abstract class ReloadableApplication<S> {
    abstract val initState: S
    abstract fun start(state: S)
    abstract fun dispose(): S

    fun run() {
        val state: dynamic = module.hot?.let { hot ->
            hot.accept()

            hot.dispose { data ->
                data.appState = this.dispose()
            }

            hot.data
        }

        if (document.body != null) {
            start(state?.appState ?: initState)
        } else {
            document.addEventListener("DOMContentLoaded", {
                start(state?.appState ?: initState)
            })
        }
    }
}
