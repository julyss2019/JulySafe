package com.github.julyss2019.bukkit.julysafe.core.entity.filter

import org.bukkit.entity.Entity

class EnumEntityFilter : RegexesEntityFilter() {
    override fun filter(entity: Entity): Boolean {
        return matchRegex(entity.type.name)
    }

    override fun toString(): String {
        return "EnumEntityFilter(id=$id, regexes=$regexes)"
    }
}