package com.github.julyss2019.bukkit.julysafe.core.listener

import com.github.julyss2019.bukkit.julysafe.core.module.RedstoneLimitModule
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockRedstoneEvent


// 预处理，转发 Event
class RedstoneLimitListener(private val module: RedstoneLimitModule) : Listener {
    private val redstoneDetector = module.detector

    @EventHandler fun onBlockRedstoneEvent(event: BlockRedstoneEvent) {
        val block = event.block

        if (!redstoneDetector.worldSet.contains(block.world)) {
            return
        }

        if (redstoneDetector.blockWhitelist.contains(block.type)) {
            return
        }

        // 过滤衰减
        if (event.newCurrent < event.oldCurrent) {
            return
        }

        module.detector.detect(event)
    }
}