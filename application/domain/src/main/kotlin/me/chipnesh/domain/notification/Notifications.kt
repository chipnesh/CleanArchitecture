package me.chipnesh.domain.notification

interface Notifications {

    suspend fun sendNotification(to: String, body: String)
}