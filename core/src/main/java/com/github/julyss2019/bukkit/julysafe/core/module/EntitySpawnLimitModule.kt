package com.github.julyss2019.bukkit.julysafe.core.module

import com.github.julyss2019.bukkit.julysafe.core.entity.EntitySet
import com.github.julyss2019.bukkit.julysafe.core.listener.EntitySpawnLimitListener
import com.github.julyss2019.bukkit.julysafe.core.module.support.WorldSetSupport
import com.github.julyss2019.bukkit.julysafe.core.world.WorldSet
import com.github.julyss2019.bukkit.voidframework.yaml.DefaultValue
import com.github.julyss2019.bukkit.voidframework.yaml.Section

@Module.YamlSectionId("entity_spawn_limit")
class EntitySpawnLimitModule : BaseModule(), WorldSetSupport {
    class Limit(val id: String, val cancelled: Boolean, val interval: Int, val spawnReasonRegexes: List<Regex>, val entitySet: EntitySet)

    override lateinit var worldSet: WorldSet

    lateinit var limits: List<Limit>

    override fun setProperties(section: Section) {
        limits = section.getSection("limits")
            .subSections
            .map { subSection ->
                Limit(subSection.name,
                    subSection.getBoolean("cancelled"),
                    subSection.getInt("interval", DefaultValue.of(-1)),
                    subSection.getStringList("spawn_reasons").map { it.toRegex() },
                    EntitySet.Parser.parse(subSection.getSection("entity_set"))
                )
            }
    }

    override fun onEnable() {
        context.registerListener(EntitySpawnLimitListener(this))
    }
}