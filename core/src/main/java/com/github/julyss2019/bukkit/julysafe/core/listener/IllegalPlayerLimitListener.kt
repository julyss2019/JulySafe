package com.github.julyss2019.bukkit.julysafe.core.listener

import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getNameAndUuid
import com.github.julyss2019.bukkit.julysafe.core.module.IllegalPlayerLimitModule
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class IllegalPlayerLimitListener(private val module: IllegalPlayerLimitModule) : Listener {

    @EventHandler
    fun onPlayerQuitEvent(event: PlayerQuitEvent) {
        val player = event.player

        if (module.deopOnQuit && player.isOp) {
            player.isOp = false
            module.info("deop op on quit, player = ${player.getNameAndUuid()}.")
        }

        if (module.setSurvivalModeOnQuit && player.gameMode == GameMode.CREATIVE) {
            player.gameMode = GameMode.SURVIVAL
            module.info("set survival mode on quit, player = ${player.getNameAndUuid()}.")
        }
    }
}