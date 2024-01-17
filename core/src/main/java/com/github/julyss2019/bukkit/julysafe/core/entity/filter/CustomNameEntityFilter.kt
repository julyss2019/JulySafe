package com.github.julyss2019.bukkit.julysafe.core.entity.filter

import org.bukkit.entity.Entity

class CustomNameEntityFilter : RegexesEntityFilter() {
    override fun filter(entity: Entity): Boolean {
        return try {
            matchRegex(entity.customName)
        } catch (ex: NoClassDefFoundError) {
            false
        }
    }

    override fun toString(): String {
        return "CustomNameEntityFilter(id=$id, regexes=$regexes)"
    }
}