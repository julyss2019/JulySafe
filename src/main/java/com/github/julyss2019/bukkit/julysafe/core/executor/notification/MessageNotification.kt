package com.github.julyss2019.bukkit.julysafe.core.executor.notification

import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.Bukkit

class MessageNotification : Notification {
    private lateinit var message: String

    override fun notifyAll(messageProcessor: Notification.MessageProcessor) {
        Bukkit.broadcastMessage(messageProcessor.process(message))
    }

    override fun setProperties(section: Section) {
        message = section.getString("message")
    }
}