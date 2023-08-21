package com.github.julyss2019.bukkit.plugins.julysafe.api.event

import org.bukkit.entity.Item
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class DropsCleanedEvent(val drops: List<Item>) : Event() {
    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }
}