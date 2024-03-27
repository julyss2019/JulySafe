package com.github.julyss2019.bukkit.julysafe.core.listener

import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getAsSimpleString
import com.github.julyss2019.bukkit.julysafe.core.module.EntitySpawnLimitModule
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.CreatureSpawnEvent

class EntitySpawnLimitListener(private val module: EntitySpawnLimitModule) : Listener {
    private val lastSpawnMap = mutableMapOf<EntitySpawnLimitModule.Limit, Long>()

    @EventHandler
    fun onEntitySpawnEvent(event: CreatureSpawnEvent) {
        val entity = event.entity

        if (!module.worldSet.contains(entity.world)) {
            return
        }

        val spawnReason = event.spawnReason

        for (limit in module.limits) {
            if (!limit.spawnReasonRegexes.any { it.matches(spawnReason.name) }) {
                continue
            }

            if (!limit.entitySet.contains(entity)) {
                continue
            }

            if (limit.cancelled) {
                event.isCancelled = true
                module.debug("direct cancelled, spawn_reason = $spawnReason, entity = ${entity.getAsSimpleString()}, location = ${entity.location}.")
                return
            }

            if (limit.interval != -1) {
                if (!lastSpawnMap.contains(limit)) {
                    lastSpawnMap[limit] = System.currentTimeMillis()
                }

                val interval = System.currentTimeMillis() - lastSpawnMap[limit]!!

                if (interval < limit.interval) {
                    event.isCancelled = true
                    module.debug("interval cancelled, spawn_reason = $spawnReason, entity = ${entity.getAsSimpleString()}, location = ${entity.location}.")
                } else {
                    lastSpawnMap[limit] = System.currentTimeMillis()
                }
            }
        }
    }
}