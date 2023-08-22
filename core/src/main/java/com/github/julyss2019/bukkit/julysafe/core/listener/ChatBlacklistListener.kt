package com.github.julyss2019.bukkit.julysafe.core.listener

import com.github.julyss2019.bukkit.julysafe.core.Permission
import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getNameAndUuid
import com.github.julyss2019.bukkit.julysafe.core.module.ChatBlacklistModule
import com.github.julyss2019.bukkit.voidframework.common.Messages
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class ChatBlacklistListener(private val module: ChatBlacklistModule) : Listener {
    private val localeResource = module.getLocalResource()

    @EventHandler(priority = EventPriority.MONITOR)
    fun onAsyncPlayerChatEvent(event: AsyncPlayerChatEvent) {
        val player = event.player

        if (module.context.plugin.checkPermission(player, com.github.julyss2019.bukkit.julysafe.core.Permission.BYPASS_CHAT_BLACKLIST)) {
            return
        }

        val message = event.message

        module.blacklistRegexes.forEach { regex ->
            val matchResult = regex.find(message)

            if (matchResult != null) {
                if (module.cancelEvent) {
                    event.isCancelled = true

                    module.debug("cancelled, player = ${player.getNameAndUuid()}, message = ${event.message}.")
                    Messages.sendColoredMessage(player, localeResource.getString("cancelled"))
                } else {
                    var processedMessage = message

                    module.blacklistRegexes.forEach { regex1 ->
                        processedMessage = processedMessage.replace(regex1, module.replaceString)
                    }
                    module.debug("replaced, player = ${player.getNameAndUuid()}, old_message = ${event.message}, new_message = $processedMessage.")
                    Messages.sendColoredMessage(player, localeResource.getString("replaced"))

                    event.message = processedMessage
                }

                return
            }
        }
    }
}
