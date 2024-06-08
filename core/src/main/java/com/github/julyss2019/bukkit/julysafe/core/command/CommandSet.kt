package com.github.julyss2019.bukkit.julysafe.core.command

import com.github.julyss2019.bukkit.julysafe.core.InclusiveExclusiveSet
import com.github.julyss2019.bukkit.voidframework.yaml.Section

class CommandSet(override val includes: List<Regex>, override val excludes: List<Regex>) :
    InclusiveExclusiveSet<Regex> {
    fun contains(command: String): Boolean {
        return !excludes.any { it.matches(command) } && includes.any { it.matches(command) }
    }

    object Parser {
        fun parse(section: Section): CommandSet {
            return CommandSet(
                includes = section.getStringList("includes").map { it.toRegex() },
                excludes = section.getStringList("excludes").map { it.toRegex() })
        }
    }
}