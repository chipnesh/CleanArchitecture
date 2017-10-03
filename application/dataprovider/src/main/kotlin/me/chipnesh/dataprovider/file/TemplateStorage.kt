package me.chipnesh.dataprovider.file

import me.chipnesh.domain.template.TemplateGateway
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

class TemplateStorage(
        private val htmlTemplateEngine: TemplateEngine
) : TemplateGateway {
    override fun buildTemplate(name: String, parameters: Map<String, Any>): String? {
        val ctx = Context()
        parameters.forEach { k, v -> ctx.setVariable(k, v) }
        return htmlTemplateEngine.process(name, ctx)
    }
}