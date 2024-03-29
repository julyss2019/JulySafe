package com.github.julyss2019.bukkit.julysafe.core.executor.notification

import com.github.julyss2019.bukkit.julysafe.core.executor.notification.message.processor.MessageProcessor
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.Bukkit

class MessageNotification : Notification {
    private lateinit var message: String
    override fun notifyCountdown(messageProcessor: MessageProcessor, currentCountdown: Int, maxCountdown: Int) {
        notifyCompleted(messageProcessor)
    }

    override fun notifyCompleted(messageProcessor: MessageProcessor) {
        Bukkit.broadcastMessage(messageProcessor.process(message))
    }

    override fun setProperties(section: Section) {
        message = section.getString("message")
    }
}