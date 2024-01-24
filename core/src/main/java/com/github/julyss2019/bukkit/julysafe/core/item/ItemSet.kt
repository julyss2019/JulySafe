package com.github.julyss2019.bukkit.julysafe.core.item

import com.github.julyss2019.bukkit.julysafe.core.InclusiveExclusiveSet
import com.github.julyss2019.bukkit.julysafe.core.item.filter.CoreItemFilter
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.World
import org.bukkit.entity.Item

class ItemSet(override val includes: List<CoreItemFilter>, override val excludes: List<CoreItemFilter>) : InclusiveExclusiveSet<CoreItemFilter> {
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
            return ItemSet(includes = section.getSection("includes").subSections.map { CoreItemFilter.Parser.parse(it) },
                excludes = section.getSection("excludes").subSections.map { CoreItemFilter.Parser.parse(it) })
        }
    }
}