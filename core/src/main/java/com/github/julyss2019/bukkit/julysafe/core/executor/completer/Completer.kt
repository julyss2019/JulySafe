package com.github.julyss2019.bukkit.julysafe.core.executor.completer

import com.github.julyss2019.bukkit.julysafe.core.executor.notification.Notification
import com.github.julyss2019.bukkit.voidframework.yaml.Section


class Completer(val notification: Notification) {
    object Parser{
        fun parse(section: Section): Completer {
            return Completer(Notification.Parser.parse(section.getSection("notification")))
        }
    }
}