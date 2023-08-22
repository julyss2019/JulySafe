package com.github.julyss2019.bukkit.julysafe.core.listener

import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getNameAndUuid
import com.github.julyss2019.bukkit.julysafe.core.module.CropTrampleLimitModule
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class CropTrampleLimitListener(private val module: CropTrampleLimitModule) : Listener {
    companion object {
        private var material: Material = try {
            Material.FARMLAND // 高于 1.12.2
        } catch (ex: NoSuchFieldError) {
            Material.valueOf("SOIL") // 低于或等于 1.12
        }
    }

    @EventHandler
    fun onPlayerInteractEvent(event: PlayerInteractEvent) {
        val block = event.clickedBlock ?: return

        if (!module.worldSet.contains(block.world)) {
            return
        }

        val player = event.player

        if (event.action == Action.PHYSICAL && event.hand == null && block.type == material) {
            event.isCancelled = true
            module.debug("cancelled, player = ${player.getNameAndUuid()}, location = ${block.location}.")
        }
    }
}