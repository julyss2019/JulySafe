package com.github.julyss2019.bukkit.julysafe.core.listener

import com.github.julyss2019.bukkit.julysafe.core.Permission
import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getNameAndUuid
import com.github.julyss2019.bukkit.julysafe.core.module.ChatSpamLimitModule
import com.github.julyss2019.bukkit.julysafe.core.util.CooldownTimer
import com.github.julyss2019.bukkit.voidframework.common.Messages
import com.github.julyss2019.bukkit.voidframework.text.PlaceholderContainer
import com.github.julyss2019.bukkit.voidframework.text.Texts
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class ChatSpamLimitListener(private val module: ChatSpamLimitModule) : Listener {
    private val localeResource = module.getLocalResource()
    private val lastChatMap = ConcurrentHashMap<UUID, CooldownTimer>()

    @EventHandler(ignoreCancelled = true)
    fun onAsyncPlayerChatEvent(event: AsyncPlayerChatEvent) {
        val player = event.player
        val playerUuid = player.uniqueId

        if (module.context.plugin.checkPermission(player, Permission.BYPASS_CHAT_SPAM_LIMIT)) {
            return
        }

        if (lastChatMap.containsKey(playerUuid)) {
            val cooldownTimer = lastChatMap[playerUuid]!!

            if (cooldownTimer.isInCooldown()) {
                Messages.sendColoredMessage(
                    player,
                    Texts.setPlaceholders(
                        localeResource.getString("denied"),
                        PlaceholderContainer().put(
                            "cooldown",
                            cooldownTimer.getFormattedRemainingCooldown()
                        )
                    )
                )

                module.debug("cancelled, player = ${player.getNameAndUuid()}.")
                event.isCancelled = true
                return
            }
        }

        lastChatMap[playerUuid] = CooldownTimer.createNewAndStart(module.threshold)
    }
}
