package com.github.julyss2019.bukkit.julysafe.core.item.filter

import com.github.julyss2019.bukkit.voidframework.common.Items
import org.bukkit.entity.Item
import org.bukkit.inventory.ItemStack

class LoreItemFilter : RegexesItemFilter() {
    override fun filter(item: Item): Boolean {
        return Items.getLores(item.itemStack).any { matchRegex(it) }
    }

    override fun toString(): String {
        return "LoreItemFilter(id = $id, regexes = $regexes)"
    }
}