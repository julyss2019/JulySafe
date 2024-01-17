package com.github.julyss2019.bukkit.julysafe.core.executor.notification

import com.github.julyss2019.bukkit.julysafe.core.executor.notification.message.processor.MessageProcessor
import com.github.julyss2019.bukkit.voidframework.yaml.DefaultValue
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.Bukkit

class TitleNotification : Notification {
    private lateinit var title: String
    private lateinit var subtitle: String
    private var fadeIn: Int = -1
    private var stay: Int = -1
    private var fadeOut: Int = -1

    override fun notifyAll(messageProcessor: MessageProcessor) {
        Bukkit.getOnlinePlayers().forEach {
            it.sendTitle(
                messageProcessor.process(title),
                messageProcessor.process(subtitle),
                fadeIn, stay, fadeOut
            )
        }
    }

    override fun setProperties(section: Section) {
        title = section.getString("title")
        subtitle = section.getString("subtitle")
        fadeIn = section.getInt("fade_in", DefaultValue.of(20))
        stay = section.getInt("stay", DefaultValue.of(20))
        fadeOut = section.getInt("fade_out", DefaultValue.of(20))
    }
}