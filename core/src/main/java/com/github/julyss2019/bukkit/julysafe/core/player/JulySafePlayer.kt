package com.github.julyss2019.bukkit.julysafe.core.player

import com.github.julyss2019.bukkit.julysafe.core.util.MessageUtils
import org.bukkit.entity.Player

class JulySafePlayer(val bukkitPlayer: Player) {
    var lastRedstoneLimitNotify: Long = -1L
    var debugEnabled: Boolean = false

    fun debug(message: String) {
        MessageUtils.sendMessage(bukkitPlayer, "[Debug] &f$message")
    }
}