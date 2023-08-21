package com.github.julyss2019.bukkit.julysafe.core.player

import org.bukkit.OfflinePlayer
import java.util.*

class JulySafePlayerManager {
    private val playerMap = mutableMapOf<UUID, JulySafePlayer>()

    fun getJulySafePlayer(uuid: UUID): JulySafePlayer {
        if (!playerMap.containsKey(uuid)) {
            playerMap[uuid] = JulySafePlayer(uuid)
        }

        return playerMap[uuid]!!
    }

    fun getJulySafePlayer(offlinePlayer: OfflinePlayer): JulySafePlayer {
        return getJulySafePlayer(offlinePlayer.uniqueId)
    }

    fun unloadJulySafePlayer(offlinePlayer: OfflinePlayer) {
        unloadJulySafePlayer(offlinePlayer.uniqueId)
    }

    fun unloadJulySafePlayer(uuid: UUID) {
        playerMap.remove(uuid)
    }
}