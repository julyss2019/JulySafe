package com.github.julyss2019.bukkit.julysafe.core.entity.filter

import com.github.julyss2019.bukkit.julysafe.api.EntityFilter
import com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import com.void01.bukkit.voidframework.api.common.VoidFramework2
import com.void01.bukkit.voidframework.api.common.groovy.GroovyBinding
import com.void01.bukkit.voidframework.api.common.groovy.GroovyConfig
import org.bukkit.entity.Entity

class GroovyEntityFilter : BaseEntityFilter() {
    private lateinit var entityFilter: EntityFilter
    override fun filter(entity: Entity): Boolean {
        return VoidFramework2.getGroovyManager().eval(script, GroovyConfig().apply {
            this.parentClassLoader = JulySafePlugin::class.java.classLoader
        }, GroovyBinding().apply {
            setVariable("entity", entity)
        }) as Boolean
    }

    override fun setProperties(section: Section) {
        script = section.getString("script")
    }

    override fun toString(): String {
        return "JavascriptEntityFilter(id=$id, script='$script')"
    }
}