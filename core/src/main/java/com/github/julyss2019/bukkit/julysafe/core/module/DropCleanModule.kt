package com.github.julyss2019.bukkit.julysafe.core.module


import com.github.julyss2019.bukkit.julysafe.api.event.DropsCleanedEvent
import com.github.julyss2019.bukkit.julysafe.core.executor.Executor
import com.github.julyss2019.bukkit.julysafe.core.executor.notification.message.processor.ColoredPlaceholderMessageProcessor
import com.github.julyss2019.bukkit.julysafe.core.item.ItemSet
import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getAsSimpleString
import com.github.julyss2019.bukkit.julysafe.core.module.support.ExecutorSupport
import com.github.julyss2019.bukkit.julysafe.core.module.support.ItemSetSupport
import com.github.julyss2019.bukkit.julysafe.core.module.support.WorldSetSupport
import com.github.julyss2019.bukkit.julysafe.core.world.WorldSet
import com.github.julyss2019.bukkit.voidframework.text.PlaceholderContainer
import org.bukkit.Bukkit
import org.bukkit.entity.Item

@Module.YamlSectionId("drop_clean")
class DropCleanModule : BaseModule(), WorldSetSupport, ExecutorSupport, ItemSetSupport {
    override lateinit var executor: Executor
    override lateinit var itemSet: ItemSet
    override lateinit var worldSet: WorldSet

    override fun onEnable() {
        executor.task = object : Executor.Task {
            override fun run() {
                var total = 0
                val cleanedDrops = mutableListOf<Item>()

                for (world in worldSet.getAll()) {
                    for (item in itemSet.getAll(world)) {
                        total += 1
                        item.remove()
                        cleanedDrops.add(item)
                        debug("removed, item_stack = ${item.itemStack}, location = ${item.location.getAsSimpleString()}.")
                    }
                }

                Bukkit.getPluginManager().callEvent(DropsCleanedEvent(cleanedDrops))
                executor.completer?.notification?.notifyAll(ColoredPlaceholderMessageProcessor(PlaceholderContainer().put("total", total)))
            }
        }
    }
}