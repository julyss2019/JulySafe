package com.github.julyss2019.bukkit.julysafe.core.item.filter

import com.github.julyss2019.bukkit.julysafe.core.util.GroovyUtils
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import groovy.lang.Binding
import org.bukkit.entity.Item

class GroovyItemFilter : BaseItemFilter() {
    private lateinit var script: String

    override fun filter(item: Item): Boolean {
        return GroovyUtils.eval(script, Binding().apply { setVariable("item", item) }) as Boolean
    }

    override fun setProperties(section: Section) {
        script = section.getString("script")
    }

    override fun toString(): String {
        return "EnumItemFilter(id = $id, script = $script)"
    }
}