package com.github.julyss2019.bukkit.julysafe.core.executor.notification

import com.github.julyss2019.bukkit.voidframework.text.PlaceholderContainer
import com.github.julyss2019.bukkit.voidframework.text.Texts
import com.github.julyss2019.bukkit.voidframework.yaml.Section

interface Notification {
    interface MessageProcessor {
        fun process(message: String): String
    }

    class ColoredPlaceholderMessageProcessor(private val placeholderContainer: PlaceholderContainer) : MessageProcessor {
        override fun process(message: String): String {
            return message
                .let { Texts.getColoredText(it) }
                .let { Texts.setPlaceholders(it, placeholderContainer) }
        }
    }

    fun notifyAll(messageProcessor: MessageProcessor)

    fun clear() {}

    fun setProperties(section: Section)

    enum class Type(val classMapping: Class<out Notification>) {
        TITLE(TitleNotification::class.java),
        ACTION_BAR(ActionBarNotification::class.java),
        MESSAGE(MessageNotification::class.java),
        BOSS_BAR(BossBarNotification::class.java)
    }

    object Parser {
        fun parse(section: Section): Notification {
            section.getEnum("type", Type::class.java).classMapping.newInstance().let {
                it.setProperties(section.getSection("properties"))
                return it
            }
        }
    }
}