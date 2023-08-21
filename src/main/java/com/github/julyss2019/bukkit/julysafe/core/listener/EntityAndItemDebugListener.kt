package com.github.julyss2019.bukkit.julysafe.core.listener

import com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin
import com.github.julyss2019.bukkit.julysafe.core.entity.EntitySet
import com.github.julyss2019.bukkit.julysafe.core.item.ItemSet
import com.github.julyss2019.bukkit.julysafe.core.module.ChunkEntityLimitModule
import com.github.julyss2019.bukkit.julysafe.core.module.DropCleanModule
import com.github.julyss2019.bukkit.julysafe.core.module.EntitySpawnLimitModule
import com.github.julyss2019.bukkit.julysafe.core.module.support.EntitySetSupport
import com.github.julyss2019.bukkit.voidframework.common.Items
import org.bukkit.entity.Entity
import org.bukkit.entity.Item
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.EquipmentSlot

class EntityAndItemDebugListener(private val plugin: JulySafePlugin) : Listener {
    private val julySafePlayerManager = plugin.julySafePlayerManager

    @Suppress("DuplicatedCode")
    @EventHandler fun onPlayerPickupItemEvent(event: PlayerDropItemEvent) {
        val item = event.itemDrop
        val itemStack = item.itemStack
        val julySafePlayer = julySafePlayerManager.getJulySafePlayer(event.player)

        if (julySafePlayer.debugEnabled) {
            julySafePlayer.debug("item: $item(type = ${itemStack.type}, display_name = ${Items.getDisplayName(itemStack)}, lores = ${Items.getLores(itemStack)}).")

            plugin.moduleManager.getModuleByClass(DropCleanModule::class.java)!!.let {
                if (it.enabled) {
                    julySafePlayer.debug(getItemSetMatchMessage(item, it.itemSet))
                }
            }

            event.isCancelled = true
        }
    }

    @Suppress("DuplicatedCode")
    @EventHandler fun onPlayerInteractEntityEvent(event: PlayerInteractEntityEvent) {
        if (event.hand != EquipmentSlot.HAND) {
            return
        }

        val rightClicked = event.rightClicked
        val julySafePlayer = julySafePlayerManager.getJulySafePlayer(event.player)

        if (julySafePlayer.debugEnabled) {
            julySafePlayer.debug("entity: $rightClicked(type = ${rightClicked.type}, custom_name = ${rightClicked.customName}, name = ${rightClicked.name}).")

            plugin.moduleManager.getEnabledModules()
                .forEach { module ->
                    if (module is EntitySetSupport) {
                        julySafePlayer.debug("${module.javaClass.simpleName}: ${getEntitySetMatchMessage(rightClicked, module.entitySet)}.")
                    } else if (module is ChunkEntityLimitModule) {
                        module.limits.forEach {
                            julySafePlayer.debug("${module.javaClass.simpleName}(${it.id}): ${getEntitySetMatchMessage(rightClicked, it.entitySet)}.")
                        }
                    } else if (module is EntitySpawnLimitModule) {
                        module.limits.forEach {
                            julySafePlayer.debug("${module.javaClass.simpleName}(${it.id}): ${getEntitySetMatchMessage(rightClicked, it.entitySet)}.")
                        }
                    }
                }
        }
    }

    private fun getItemSetMatchMessage(item: Item, itemSet: ItemSet): String {
        itemSet.run {
            for (exclude in excludes) {
                if (exclude.filter(item)) {
                    return "excluded by ${exclude.javaClass.simpleName}(${exclude.id})"
                }
            }

            for (include in includes) {
                if (include.filter(item)) {
                    return "included by ${include.javaClass.simpleName}(${include.id})"
                }
            }

            return "non-match"
        }
    }

    private fun getEntitySetMatchMessage(entity: Entity, entitySet: EntitySet): String {
        entitySet.run {
            for (exclude in excludes) {
                if (exclude.filter(entity)) {
                    return "excluded by ${exclude.javaClass.simpleName}(${exclude.id})"
                }
            }

            for (include in includes) {
                if (include.filter(entity)) {
                    return "included by ${include.javaClass.simpleName}(${include.id})"
                }
            }

            return "non-match"
        }
    }
}