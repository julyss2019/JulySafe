package com.github.julyss2019.bukkit.julysafe.core.entity.filter

import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.entity.Entity

class HasPassengersEntityFilter : BaseEntityFilter() {
    override fun filter(entity: Entity): Boolean {
        return entity.passengers.isNotEmpty()
    }

    override fun setProperties(section: Section) {
    }

    override fun toString(): String {
        return "HasPassengersEntityFilter(id=$id)"
    }
}