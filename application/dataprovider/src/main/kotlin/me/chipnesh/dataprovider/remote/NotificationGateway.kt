package me.chipnesh.dataprovider.remote

import me.chipnesh.domain.notification.Notifications
import org.springframework.kotlin.experimental.coroutine.event.CoroutineApplicationEventPublisher

data class SendNotificationEvent(
        val to: String,
        val body: String
)

class NotificationGateway(
        private val publisher: CoroutineApplicationEventPublisher
) : Notifications {

    suspend override fun sendNotification(to: String, body: String)
            = publisher.publishEvent(SendNotificationEvent(to, body))
}