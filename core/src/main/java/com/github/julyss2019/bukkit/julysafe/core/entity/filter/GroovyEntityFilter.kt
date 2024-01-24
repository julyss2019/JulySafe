package com.github.julyss2019.bukkit.julysafe.core.entity.filter

import com.github.julyss2019.bukkit.julysafe.api.EntityFilter
import com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import com.void01.bukkit.voidframework.api.common.VoidFramework2
import com.void01.bukkit.voidframework.api.common.groovy.GroovyConfig
import org.bukkit.entity.Entity
import java.io.File

class GroovyEntityFilter : BaseEntityFilter() {
    private lateinit var entityFilter: EntityFilter
    override fun filter(entity: Entity): Boolean {
        return entityFilter.filter(entity)
    }

    override fun setProperties(section: Section) {
        val scriptFile = File(JulySafePlugin.instance.dataFolder, section.getString("file"))

        entityFilter = VoidFramework2.getGroovyManager().parseClass(scriptFile, GroovyConfig().apply {
            sourceEncoding = "UTF-8"
        }).newInstance() as EntityFilter
    }

    override fun toString(): String {
        return "GroovyEntityFilter(entityFilter=$entityFilter)"
    }
}