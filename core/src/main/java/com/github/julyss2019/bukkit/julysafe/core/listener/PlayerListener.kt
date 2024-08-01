package com.github.julyss2019.bukkit.julysafe.core.listener

import com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListener(plugin: JulySafePlugin) : Listener {
    private val julySafePlayerManager = plugin.julySafePlayerManager

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        julySafePlayerManager.unloadJulySafePlayer(event.player)
    }
}