package com.github.julyss2019.bukkit.julysafe.core.listener

import com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class BossBarListener(plugin: com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin) : Listener {
    private val bossBarManager = plugin.bossBarManager

    @EventHandler
    fun onPlayerJoinEvent(event: PlayerJoinEvent) {
        bossBarManager.getBars().forEach {
            it.addPlayer(event.player)
        }
    }

    @EventHandler
    fun onPlayerQuitEvent(event: PlayerQuitEvent) {
        bossBarManager.getBars().forEach {
            it.removePlayer(event.player)
        }
    }
}