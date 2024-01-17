package com.github.julyss2019.bukkit.julysafe.core.executor.notification.message.processor

import com.github.julyss2019.bukkit.voidframework.text.PlaceholderContainer
import com.github.julyss2019.bukkit.voidframework.text.Texts

class ColoredPlaceholderMessageProcessor(private val placeholderContainer: PlaceholderContainer) : MessageProcessor {
    override fun process(message: String): String {
        return message
            .let { Texts.getColoredText(it) }
            .let { Texts.setPlaceholders(it, placeholderContainer) }
    }
}