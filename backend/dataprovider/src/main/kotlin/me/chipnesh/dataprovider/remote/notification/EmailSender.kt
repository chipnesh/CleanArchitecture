package me.chipnesh.dataprovider.remote.notification

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.run
import me.chipnesh.dataprovider.remote.SendNotificationEvent
import org.springframework.context.event.EventListener
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper

class EmailSender(
        private val mailSender: JavaMailSender
) : NotificationSender<SendNotificationEvent> {

    @EventListener
    override suspend fun onSend(event: SendNotificationEvent) = run(CommonPool) {
        val mimeMessage = mailSender.createMimeMessage()
        val message = MimeMessageHelper(mimeMessage, false, "UTF-8")

        message.setSubject("Example HTML email")
        message.setFrom("denis090712@google.com")
        message.setText(event.body, true)
        message.setTo(event.to)

        mailSender.send(mimeMessage)
    }
}