package me.chipnesh.domain.account

import me.chipnesh.domain.Result
import me.chipnesh.domain.UseCase
import me.chipnesh.domain.notification.SendNotification
import me.chipnesh.domain.notification.SendNotificationRequest

data class SendRegisteredNotificationRequest(val to: String, val parameters: Map<String, Any>)

class SendRegisteredNotification(
        private val sendNotification: SendNotification
) : UseCase<SendRegisteredNotificationRequest, Unit> {

    override fun execute(request: SendRegisteredNotificationRequest): Result<Unit> {
        val notificationRequest = SendNotificationRequest(request.to, "registration", request.parameters)
        return sendNotification.execute(notificationRequest)
    }
}