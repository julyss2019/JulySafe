package com.github.julyss2019.bukkit.julysafe.core.entity.filter

import com.github.julyss2019.bukkit.julysafe.core.util.GroovyUtils
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import groovy.lang.Binding
import org.bukkit.entity.Entity
import javax.script.SimpleBindings

class GroovyEntityFilter : BaseEntityFilter() {
    private lateinit var script: String

    override fun filter(entity: Entity): Boolean {
        return GroovyUtils.eval(script, Binding().apply { setVariable("entity", entity) }) as Boolean
    }

    override fun setProperties(section: Section) {
        script = section.getString("script")
    }

    override fun toString(): String {
        return "JavascriptEntityFilter(id=$id, script='$script')"
    }
}