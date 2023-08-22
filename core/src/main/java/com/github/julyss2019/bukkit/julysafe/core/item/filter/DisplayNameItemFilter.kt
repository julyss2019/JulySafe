package com.github.julyss2019.bukkit.julysafe.core.item.filter

import com.github.julyss2019.bukkit.voidframework.common.Items
import org.bukkit.entity.Item

class DisplayNameItemFilter : RegexesItemFilter() {
    override fun filter(item: Item): Boolean {
        val displayName: String = Items.getDisplayName(item.itemStack) ?: return false

        if (displayName == "") {
            return false
        }

        return matchRegex(displayName)
    }

    override fun toString(): String {
        return "DisplayNameItemFilter(id = $id, regexes = $regexes)"
    }
}