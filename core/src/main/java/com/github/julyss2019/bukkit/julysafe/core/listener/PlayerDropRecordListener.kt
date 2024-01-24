package com.github.julyss2019.bukkit.julysafe.core.listener

import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getAsSimpleString
import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getNameAndUuid
import com.github.julyss2019.bukkit.julysafe.core.module.PlayerDropRecordModule
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent

class PlayerDropRecordListener(private val module: PlayerDropRecordModule) : Listener {
    @EventHandler
    fun onPlayerDropItemEvent(event: PlayerDropItemEvent) {
        val itemDrop = event.itemDrop

        if (module.worldSet.contains(itemDrop.world)) {
            module.debug("record, player = ${event.player.getNameAndUuid()}, item = ${itemDrop.getAsSimpleString()}.")
        }
    }
}