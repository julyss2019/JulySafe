package com.github.julyss2019.bukkit.julysafe.core.module

import com.github.julyss2019.bukkit.julysafe.core.entity.EntitySet
import com.github.julyss2019.bukkit.julysafe.core.executor.Executor
import com.github.julyss2019.bukkit.julysafe.core.executor.notification.message.processor.ColoredPlaceholderMessageProcessor
import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getAsSimpleString
import com.github.julyss2019.bukkit.julysafe.core.module.support.ExecutorSupport
import com.github.julyss2019.bukkit.julysafe.core.module.support.WorldSetSupport
import com.github.julyss2019.bukkit.julysafe.core.world.WorldSet
import com.github.julyss2019.bukkit.voidframework.text.PlaceholderContainer
import com.github.julyss2019.bukkit.voidframework.yaml.Section

@Module.YamlSectionId("chunk_entity_limit")
class ChunkEntityLimitModule : BaseModule(), WorldSetSupport, ExecutorSupport {
    class Limit(val id: String, val threshold: Int, val entitySet: EntitySet)

    override lateinit var executor: Executor
    override lateinit var worldSet: WorldSet
    lateinit var limits: List<Limit>

    override fun setProperties(section: Section) {
        limits = section.getSection("limits")
            .subSections
            .map {
                Limit(it.name, it.getInt("threshold"), EntitySet.Parser.parse(it.getSection("entity_set")))
            }
    }

    override fun onEnable() {
        executor.task = object : Executor.Task {
            override fun run() {
                var removed = 0

                worldSet.getAll()
                    .map { it.loadedChunks }
                    .flatMap { it.asIterable() }
                    .forEach { chunk ->
                        for (limit in limits) {
                            var counter = 0

                            for (entity in chunk.entities) {
                                if (limit.entitySet.contains(entity)) {
                                    counter++

                                    if (counter > limit.threshold) {
                                        entity.remove()
                                        debug("removed, entity = ${entity.getAsSimpleString()}, chunk = ${chunk.getAsSimpleString()}, location = ${entity.location.getAsSimpleString()}")
                                        removed++
                                    }
                                }
                            }
                        }
                    }
                executor.completer?.notification?.notifyCompleted(
                    ColoredPlaceholderMessageProcessor(
                        PlaceholderContainer().put("total", removed)
                    )
                )
            }
        }
    }
}