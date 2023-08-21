package com.github.julyss2019.bukkit.julysafe.core.entity.filter

import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.entity.Entity

class MetadataEntityFilter : BaseEntityFilter() {
    private lateinit var keys : List<String>

    override fun filter(entity: Entity): Boolean {
        return keys.any {
            return entity.hasMetadata(it)
        }
    }

    override fun setProperties(section: Section) {
        keys = section.getStringList("keys")
    }

    override fun toString(): String {
        return "MetadataEntityFilter(id=$id, keys=$keys)"
    }
}