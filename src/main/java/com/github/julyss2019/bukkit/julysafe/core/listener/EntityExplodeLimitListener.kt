package com.github.julyss2019.bukkit.julysafe.core.listener

import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getAsSimpleString
import com.github.julyss2019.bukkit.julysafe.core.module.BlockExplodeLimitModule
import com.github.julyss2019.bukkit.julysafe.core.module.EntityExplodeLimitModule
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.entity.EntityExplodeEvent

class EntityExplodeLimitListener(private val module: EntityExplodeLimitModule) : Listener {
    @EventHandler
    fun onEntityExplodeEvent(event: EntityExplodeEvent) {
        val entity = event.entity

        if (!module.worldSet.contains(entity.world)) {
            return
        }

        event.isCancelled = true
        module.debug("cancelled, entity = ${entity.getAsSimpleString()}.")
    }
}