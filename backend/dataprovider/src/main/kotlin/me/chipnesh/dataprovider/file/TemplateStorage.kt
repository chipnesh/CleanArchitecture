package me.chipnesh.dataprovider.file

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.run
import me.chipnesh.domain.template.Templates
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

class TemplateStorage(
        private val htmlTemplateEngine: TemplateEngine
) : Templates {

    override suspend fun buildTemplate(name: String, parameters: Map<String, Any>): String? = run(CommonPool) {
        val ctx = Context()
        parameters.forEach { k, v -> ctx.setVariable(k, v) }
        htmlTemplateEngine.process(name, ctx)
    }
}