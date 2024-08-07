package com.github.julyss2019.bukkit.julysafe.core.listener

import com.github.julyss2019.bukkit.julysafe.core.Permission
import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getNameAndUuid
import com.github.julyss2019.bukkit.julysafe.core.module.CommandLimitModule
import com.github.julyss2019.bukkit.voidframework.common.Messages
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class CommandLimitListener(private val module: CommandLimitModule) : Listener {
    private val localeResource = module.getLocalResource()

    @EventHandler(ignoreCancelled = true)
    fun onPlayerCommandPreprocessEvent(event: PlayerCommandPreprocessEvent) {
        val commandLine = event.message
        val player = event.player

        if (module.context.plugin.checkPermission(player, Permission.BYPASS_COMMAND_BLACKLIST)) {
            return
        }

        if (module.commandSet.contains(commandLine)) {
            event.isCancelled = true
            Messages.sendColoredMessage(player, localeResource.getString("denied"))
            module.debug("cancelled, player = ${player.getNameAndUuid()}, command_line = $commandLine")
            return
        }
    }
}