package com.github.julyss2019.bukkit.julysafe.core.util

import com.github.julyss2019.bukkit.voidframework.common.Messages
import org.bukkit.command.CommandSender

object MessageUtils {
    fun sendMessage(sender: CommandSender, message: String) {
        Messages.sendColoredMessage(sender, "&a[JulySafe] &f$message")
    }
}