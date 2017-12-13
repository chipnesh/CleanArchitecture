package me.chipnesh.dataprovider.remote.notification

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.run
import me.chipnesh.dataprovider.remote.SendNotificationEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener

class ConsoleSender : NotificationSender<SendNotificationEvent> {
    companion object {
        val log: Logger = LoggerFactory.getLogger(ConsoleSender::class.java)
    }

    @EventListener
    override suspend fun onSend(event: SendNotificationEvent) = run(CommonPool) {
        log.info(event.body)
    }
}