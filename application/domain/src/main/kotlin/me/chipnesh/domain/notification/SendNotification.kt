package me.chipnesh.domain.notification

import me.chipnesh.domain.UseCase
import me.chipnesh.domain.Result
import me.chipnesh.domain.template.GetEmailTemplate
import me.chipnesh.domain.template.GetEmailTemplateRequest

data class SendNotificationRequest(val to: String, val template: String, val parameters: Map<String, Any>)

class SendNotification(
        private val notificationGateway: NotificationGateway,
        private val getEmailTemplate: GetEmailTemplate
) : UseCase<SendNotificationRequest, Unit> {

    override fun execute(request: SendNotificationRequest): Result<Unit> {
        val getEmailTemplateRequest = GetEmailTemplateRequest(request.template, request.parameters)
        val result = getEmailTemplate.execute(getEmailTemplateRequest)
        return when (result) {
            is Result.Success -> {
                notificationGateway.notify(request.to, result.result.template)
                Result.Success()
            }
            is Result.Failed -> result
        }
    }
}