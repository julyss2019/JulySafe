package com.github.julyss2019.bukkit.julysafe.core.world

import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.Bukkit
import org.bukkit.World

/**
 * 世界差集
 */
class WorldSet(private val includes: List<Regex>, private val excludes: List<Regex>) {
    fun contains(target: World): Boolean {
        val worldName = target.name

        for (excludeRegex in excludes) {
            if (worldName.matches(excludeRegex)) {
                return false
            }
        }

        for (includeRegex in includes) {
            if (worldName.matches(includeRegex)) {
                return true
            }
        }

        return false
    }

    fun getAll(): List<World> {
        return Bukkit.getWorlds()
            .filter {
                contains(it)
            }
    }

    override fun toString(): String {
        return "WorldSet(includes=$includes, excludes=$excludes)"
    }

    object Parser {
        fun parse(section: Section): WorldSet {
            return WorldSet(
                section.getStringList("includes").map { it.toRegex() },
                section.getStringList("excludes").map { it.toRegex() })
        }
    }
}