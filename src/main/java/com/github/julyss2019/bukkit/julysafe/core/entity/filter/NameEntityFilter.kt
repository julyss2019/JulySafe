package com.github.julyss2019.bukkit.julysafe.core.entity.filter

import org.bukkit.entity.Entity

class NameEntityFilter : RegexesEntityFilter() {
    override fun filter(entity: Entity): Boolean {
        return matchRegex(entity.name)
    }

    override fun toString(): String {
        return "NameEntityFilter(id=$id, regexes=$regexes)"
    }
}