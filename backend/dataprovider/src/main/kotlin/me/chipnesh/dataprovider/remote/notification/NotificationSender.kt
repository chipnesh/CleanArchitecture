package me.chipnesh.dataprovider.remote.notification

interface NotificationSender<in T> {
    suspend fun onSend(event: T)
}