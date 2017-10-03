package me.chipnesh.domain.notification

interface NotificationGateway {
    fun notify(to: String, body: String)
}