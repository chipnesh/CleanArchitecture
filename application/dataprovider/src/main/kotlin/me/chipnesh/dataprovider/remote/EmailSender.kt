package me.chipnesh.dataprovider.remote

import me.chipnesh.domain.notification.NotificationGateway
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper

class EmailSender(
        private val mailSender: JavaMailSender
) : NotificationGateway {
    override fun notify(to: String,  body: String) {
        val mimeMessage = mailSender.createMimeMessage()
        val message = MimeMessageHelper(mimeMessage, false, "UTF-8")

        message.setSubject("Example HTML email")
        message.setFrom("denis090712@google.com")
        message.setText(body, true)
        message.setTo(to)

        mailSender.send(mimeMessage)
    }
}