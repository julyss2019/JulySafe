package com.github.julyss2019.bukkit.julysafe.core.player

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class JulySafePlayerManager {
    private val julySafePlayerMap = mutableMapOf<UUID, JulySafePlayer>()

    fun getJulySafePlayer(bukkitPlayer: Player): JulySafePlayer {
        require(bukkitPlayer.isOnline) { "Player is offline" }

        val uuid = bukkitPlayer.uniqueId

        if (!julySafePlayerMap.containsKey(uuid)) {
            julySafePlayerMap[uuid] = JulySafePlayer(Bukkit.getPlayer(bukkitPlayer.uniqueId))
        }

        return julySafePlayerMap[uuid]!!
    }

    fun unloadJulySafePlayer(bukkitPlayer: Player) {
        julySafePlayerMap.remove(bukkitPlayer.uniqueId)
    }
}