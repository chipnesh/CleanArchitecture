package me.chipnesh.domain.template

import me.chipnesh.domain.UseCase
import me.chipnesh.domain.Result

data class GetEmailTemplateRequest(val name: String, val parameters: Map<String, Any>)
data class GetEmailTemplateResponse(val template: String)

class GetEmailTemplate(
        private val templateGateway: TemplateGateway
) : UseCase<GetEmailTemplateRequest, GetEmailTemplateResponse> {

    override fun execute(request: GetEmailTemplateRequest): Result<GetEmailTemplateResponse> {
        val template = templateGateway.buildTemplate(request.name, request.parameters)
        return if (template != null) {
            Result.Success(GetEmailTemplateResponse(template))
        } else {
            Result.Failed("Template ${request.name} not found")
        }
    }
}