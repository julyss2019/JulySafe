package com.github.julyss2019.bukkit.julysafe.core.item.filter

import org.bukkit.entity.Item

class EnchantmentItemFilter : RegexesItemFilter() {
    override fun filter(item: Item): Boolean {
        return item.itemStack.enchantments.keys.any { matchRegex(it.name) }
    }

    override fun toString(): String {
        return "EnchantmentItemFilter(id = $id, regexes = $regexes)"
    }
}