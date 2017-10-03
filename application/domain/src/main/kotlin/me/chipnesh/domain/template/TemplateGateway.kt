package me.chipnesh.domain.template

interface TemplateGateway {
    fun buildTemplate(name: String, parameters: Map<String, Any>): String?
}