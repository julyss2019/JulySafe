package com.github.julyss2019.bukkit.julysafe.core.item

import com.github.julyss2019.bukkit.julysafe.core.InclusiveExclusiveSet
import com.github.julyss2019.bukkit.julysafe.core.entity.filter.EntityFilter
import com.github.julyss2019.bukkit.julysafe.core.item.filter.ItemFilter
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.World
import org.bukkit.entity.Item
import org.bukkit.inventory.ItemStack

class ItemSet(override val includes: List<ItemFilter>, override val excludes: List<ItemFilter>) : InclusiveExclusiveSet<ItemFilter> {
    fun contains(item: Item): Boolean {
        for (filter in excludes) {
            if (filter.filter(item)) {
                return false
            }
        }

        for (filter in includes) {
            if (filter.filter(item)) {
                return true
            }
        }

        return false
    }

    fun getAll(world: World): List<Item> {
        return world.entities.filterIsInstance<Item>().filter { contains(it) }
    }

    override fun toString(): String {
        return "ItemSet(includes = $includes, excludes = $excludes)"
    }

    object Parser {
        fun parse(section: Section): ItemSet {
            return ItemSet(includes = section.getSection("includes").subSections.map { ItemFilter.Parser.parse(it) },
                excludes = section.getSection("excludes").subSections.map { ItemFilter.Parser.parse(it) })
        }
    }
}