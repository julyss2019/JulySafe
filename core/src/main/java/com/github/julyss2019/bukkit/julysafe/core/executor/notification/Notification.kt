package com.github.julyss2019.bukkit.julysafe.core.executor.notification

import com.github.julyss2019.bukkit.julysafe.core.executor.notification.message.processor.MessageProcessor
import com.github.julyss2019.bukkit.voidframework.yaml.Section

interface Notification {
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