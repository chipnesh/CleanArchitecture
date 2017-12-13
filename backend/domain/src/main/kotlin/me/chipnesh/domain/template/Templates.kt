package me.chipnesh.domain.template

interface Templates {

    suspend fun buildTemplate(name: String, parameters: Map<String, Any>): String?
}