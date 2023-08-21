package com.github.julyss2019.bukkit.julysafe.core.module

import com.github.julyss2019.bukkit.julysafe.core.entity.EntitySet
import com.github.julyss2019.bukkit.julysafe.core.executor.Executor
import com.github.julyss2019.bukkit.julysafe.core.executor.notification.Notification
import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getAsSimpleString
import com.github.julyss2019.bukkit.julysafe.core.module.support.EntitySetSupport
import com.github.julyss2019.bukkit.julysafe.core.module.support.ExecutorSupport
import com.github.julyss2019.bukkit.julysafe.core.module.support.WorldSetSupport
import com.github.julyss2019.bukkit.julysafe.core.world.WorldSet
import com.github.julyss2019.bukkit.voidframework.text.PlaceholderContainer
import com.github.julyss2019.bukkit.voidframework.yaml.Section

@Module.YamlSectionId("entity_clean") class EntityCleanModule : BaseModule(), WorldSetSupport, ExecutorSupport, EntitySetSupport {
    override lateinit var entitySet: EntitySet
    override lateinit var executor: Executor
    override lateinit var worldSet: WorldSet

    override fun setProperties(section: Section) {}

    override fun onEnable() {
        executor.task = object : Executor.Task {
            override fun run() {
                var total = 0

                for (world in worldSet.getAll()) {
                    for (entity in entitySet.getAllByWorld(world)) {
                        entity.remove()
                        total++
                        debug("removed, entity = ${entity.getAsSimpleString()}, location = ${entity.location.getAsSimpleString()}.")
                    }
                }

                executor.completer?.notification?.notifyAll(Notification.ColoredPlaceholderMessageProcessor(PlaceholderContainer().put("total", total)))
            }
        }
    }
}