package com.github.julyss2019.bukkit.julysafe.core.item.filter

import com.github.julyss2019.bukkit.julysafe.api.ItemFilter
import com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import com.void01.bukkit.voidframework.api.common.VoidFramework2
import com.void01.bukkit.voidframework.api.common.groovy.GroovyConfig
import org.bukkit.entity.Item
import java.io.File


class GroovyItemFilter : BaseItemFilter() {
    private lateinit var itemFilter: ItemFilter
    override fun filter(item: Item): Boolean {
        return itemFilter.filter(item)
    }

    override fun setProperties(section: Section) {
        val scriptFile = File(JulySafePlugin.instance.dataFolder, section.getString("file"))

        itemFilter = VoidFramework2.getGroovyManager().parseClass(scriptFile, GroovyConfig().apply {
            sourceEncoding = "UTF-8"
        }).newInstance() as ItemFilter
    }

    override fun toString(): String {
        return "GroovyEntityFilter(itemFilter=$itemFilter)"
    }
}