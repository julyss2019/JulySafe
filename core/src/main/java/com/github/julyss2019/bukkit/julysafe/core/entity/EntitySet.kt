package com.github.julyss2019.bukkit.julysafe.core.entity


import com.github.julyss2019.bukkit.julysafe.core.entity.filter.CoreEntityFilter
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

class EntitySet(override val includes: List<CoreEntityFilter>, override val excludes: List<CoreEntityFilter>) :
    com.github.julyss2019.bukkit.julysafe.core.InclusiveExclusiveSet<CoreEntityFilter> {
    fun contains(entity: Entity): Boolean {
        if (entity is Player) {
            return false
        }

        for (filter in excludes) {
            if (filter.filter(entity)) {
                return false
            }
        }

        for (filter in includes) {
            if (filter.filter(entity)) {
                return true
            }
        }

        return false
    }

    fun getAllByWorld(world: World): List<Entity> {
        return world.entities.filter { contains(it) }
    }

    override fun toString(): String {
        return "EntitySet(includes = $includes, excludes = $excludes)"
    }

    object Parser {
        fun parse(section: Section): EntitySet {
            return EntitySet(includes = section.getSection("includes").subSections.map {
                CoreEntityFilter.Parser.parse(it)
            }, excludes = section.getSection("excludes").subSections.map {
                CoreEntityFilter.Parser.parse(it)
            })
        }
    }
}