package com.github.julyss2019.bukkit.julysafe.core.item.filter

import com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import com.void01.bukkit.voidframework.api.common.VoidFramework2
import com.void01.bukkit.voidframework.api.common.groovy.GroovyBinding
import com.void01.bukkit.voidframework.api.common.groovy.GroovyConfig
import org.bukkit.entity.Item


class GroovyItemFilter : BaseItemFilter() {
    private lateinit var script: String

    override fun filter(item: Item): Boolean {
        return VoidFramework2.getGroovyManager().eval(script, GroovyConfig().apply {
            this.parentClassLoader = JulySafePlugin::class.java.classLoader
        }, GroovyBinding().apply {
            setVariable("item", item)
        }) as Boolean
    }

    override fun setProperties(section: Section) {
        script = section.getString("script")
    }

    override fun toString(): String {
        return "EnumItemFilter(id = $id, script = $script)"
    }
}