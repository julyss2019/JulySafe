package com.github.julyss2019.bukkit.julysafe.core.item.filter

import org.bukkit.entity.Item

class EnumItemFilter : RegexesItemFilter() {
    override fun filter(item: Item): Boolean {
        return matchRegex(item.itemStack.type.name)
    }

    override fun toString(): String {
        return "EnumItemFilter(id = $id, regexes = $regexes)"
    }
}