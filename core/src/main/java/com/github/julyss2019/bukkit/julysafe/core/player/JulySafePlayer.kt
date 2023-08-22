package com.github.julyss2019.bukkit.julysafe.core.player

import com.github.julyss2019.bukkit.julysafe.core.util.MessageUtils
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class JulySafePlayer(val uuid: UUID) {
    var lastRedstoneLimitNotify: Long = -1L
    var debugEnabled: Boolean = false

    fun isOnline(): Boolean {
        return getBukkitPlayer() != null
    }

    fun getBukkitPlayer(): Player? {
        return Bukkit.getPlayer(uuid)
    }

    fun debug(message: String) {
        require(isOnline()) {
            "player is offline"
        }
        MessageUtils.sendMessage(getBukkitPlayer()!!, "[Debug] &f$message")
    }
}