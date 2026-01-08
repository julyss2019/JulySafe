package com.github.julyss2019.bukkit.julysafe.core.listener

import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getAsSimpleString
import com.github.julyss2019.bukkit.julysafe.core.module.EntitySpawnLimitModule
import org.bukkit.entity.Entity
import org.bukkit.event.Cancellable
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.ProjectileLaunchEvent

class EntitySpawnLimitListener(private val module: EntitySpawnLimitModule) : Listener {
    private val lastSpawnMap = mutableMapOf<EntitySpawnLimitModule.Limit, Long>()

    @EventHandler
    fun onProjectileLaunchEvent(event: ProjectileLaunchEvent) {
        handle(event, CreatureSpawnEvent.SpawnReason.DEFAULT, event.entity)
    }

    @EventHandler
    fun onEntitySpawnEvent(event: CreatureSpawnEvent) {
        handle(event, event.spawnReason, event.entity)
    }

    private fun handle(event: Cancellable, spawnReason: CreatureSpawnEvent.SpawnReason, entity: Entity) {
        println(entity)

        if (!module.worldSet.contains(entity.world)) {
            return
        }

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