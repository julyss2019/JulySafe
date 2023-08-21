package com.github.julyss2019.bukkit.julysafe.core.listener

import com.github.julyss2019.bukkit.julysafe.core.Permission
import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getNameAndUuid
import com.github.julyss2019.bukkit.julysafe.core.module.CommandSpamLimitModule
import com.github.julyss2019.bukkit.julysafe.core.util.CooldownTimer
import com.github.julyss2019.bukkit.voidframework.common.Messages
import com.github.julyss2019.bukkit.voidframework.text.PlaceholderContainer
import com.github.julyss2019.bukkit.voidframework.text.Texts
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class CommandSpamLimitListener(private val module: CommandSpamLimitModule) : Listener {
    private val lastExecuteCommandMap = ConcurrentHashMap<UUID, CooldownTimer>()
    private val localeResource = module.getLocalResource()

    @EventHandler(ignoreCancelled = true)
    fun onPlayerCommandPreprocessEvent(event: PlayerCommandPreprocessEvent) {
        val player = event.player
        val playerUuid = player.uniqueId

        if (module.context.plugin.checkPermission(player, Permission.BYPASS_COMMAND_SPAM_LIMIT)) {
            return
        }

        if (lastExecuteCommandMap.containsKey(playerUuid)) {
            val cooldownTimer = lastExecuteCommandMap[playerUuid]!!

            if (cooldownTimer.isInCooldown()) {
                Messages.sendColoredMessage(
                    player, Texts.setPlaceholders(localeResource.getString("denied"), PlaceholderContainer().put("cooldown", cooldownTimer.getFormattedRemainingCooldown()))
                )

                module.debug("cancelled, player = ${player.getNameAndUuid()}.")
                event.isCancelled = true
                return
            }
        }

        lastExecuteCommandMap[playerUuid] = CooldownTimer.createNewAndStart(module.threshold)
    }
}
