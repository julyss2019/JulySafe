package com.github.julyss2019.bukkit.julysafe.core.listener

import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getAsSimpleString
import com.github.julyss2019.bukkit.julysafe.core.module.BlockExplodeLimitModule
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockExplodeEvent

class BlockExplodeLimitListener(private val module: BlockExplodeLimitModule) : Listener {
    @EventHandler
    fun onBlockExplodeEvent(event: BlockExplodeEvent) {
        val block = event.block

        if (module.worldSet.contains(block.world)) {
            event.isCancelled = true
            event.yield = 0F
            module.debug("cancelled, location = ${block.location.getAsSimpleString()}.")
        }
    }
}