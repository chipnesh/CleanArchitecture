package me.chipnesh.domain.template

import me.chipnesh.domain.UseCase
import me.chipnesh.domain.Result

data class GetEmailTemplateRequest(val templateName: String, val parameters: Map<String, Any>)
data class GetEmailTemplateResponse(val template: String)

class GetEmailTemplate(
        private val templates: Templates
) : UseCase<GetEmailTemplateRequest, GetEmailTemplateResponse> {

    override suspend fun execute(request: GetEmailTemplateRequest): Result<GetEmailTemplateResponse> {
        val template = templates.buildTemplate(request.templateName, request.parameters)
        return if (template != null) {
            Result.Success(GetEmailTemplateResponse(template))
        } else {
            Result.Failed("Template ${request.templateName} not found")
        }
    }
}