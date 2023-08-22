package com.github.julyss2019.bukkit.julysafe.core.listener

import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getAsSimpleString
import com.github.julyss2019.bukkit.julysafe.core.module.FireSpreadLimitModule
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBurnEvent
import org.bukkit.event.block.BlockSpreadEvent

class FireSpreadLimitListener(private val module: FireSpreadLimitModule) : Listener {
    @EventHandler
    fun onBlockSpreadEvent(event: BlockSpreadEvent) {
        val source = event.source

        if (!module.worldSet.contains(source.world)) {
            return
        }

        if (source.type == Material.FIRE) {
            event.isCancelled = true
            module.debug("BlockSpreadEvent cancelled, block = ${event.block.getAsSimpleString()}, location = ${event.block.location.getAsSimpleString()}")
        }
    }

    @EventHandler
    fun onBlockBurnEvent(event: BlockBurnEvent) {
        val block = event.block

        if (!module.worldSet.contains(block.world)) {
            return
        }

        event.isCancelled = true
        module.debug("BlockBurnEvent cancelled, block = ${block.getAsSimpleString()}, location = ${block.location.getAsSimpleString()}")
    }
}