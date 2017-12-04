package me.chipnesh.presentation.common.hmr

import me.chipnesh.presentation.common.async.launch
import kotlin.browser.document

abstract class ReloadableApplication<S> {
    abstract val initState: S
    suspend abstract fun start(state: S)
    abstract fun dispose(): S

    suspend fun run() {
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
                launch {
                    start(state?.appState ?: initState)
                }
            })
        }
    }
}
