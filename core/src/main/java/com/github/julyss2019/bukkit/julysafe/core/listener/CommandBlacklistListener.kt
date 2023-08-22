package com.github.julyss2019.bukkit.julysafe.core.listener

import com.github.julyss2019.bukkit.julysafe.core.Permission
import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getNameAndUuid
import com.github.julyss2019.bukkit.julysafe.core.module.CommandBlacklistModule
import com.github.julyss2019.bukkit.voidframework.common.Messages
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class CommandBlacklistListener(private val module: CommandBlacklistModule) : Listener {
    private val localeResource = module.getLocalResource()

    @EventHandler(ignoreCancelled = true)
    fun onPlayerCommandPreprocessEvent(event: PlayerCommandPreprocessEvent) {
        val commandLine = event.message
        val player = event.player

        if (module.context.plugin.checkPermission(player, com.github.julyss2019.bukkit.julysafe.core.Permission.BYPASS_COMMAND_BLACKLIST)) {
            return
        }

        for (blacklistRegex in module.blacklistRegexes) {
            if (blacklistRegex.find(commandLine) != null) {
                event.isCancelled = true
                Messages.sendColoredMessage(player, localeResource.getString("denied"))
                module.debug("cancelled, player = ${player.getNameAndUuid()}, command_line = $commandLine, regex = $blacklistRegex.")
                return
            }
        }
    }
}