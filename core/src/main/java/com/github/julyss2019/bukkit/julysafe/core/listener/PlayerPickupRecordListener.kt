package com.github.julyss2019.bukkit.julysafe.core.listener

import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getAsSimpleString
import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getNameAndUuid
import com.github.julyss2019.bukkit.julysafe.core.module.PlayerPickupRecordModule
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerPickupItemEvent

class PlayerPickupRecordListener(private val module: PlayerPickupRecordModule) : Listener {
    @EventHandler
    fun onPlayerDropItemEvent(event: PlayerPickupItemEvent) {
        val itemDrop = event.item

        if (module.worldSet.contains(itemDrop.world)) {
            module.debug("record, player = ${event.player.getNameAndUuid()}, item = ${itemDrop.getAsSimpleString()}.")
        }
    }
}