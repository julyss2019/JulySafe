package com.github.julyss2019.bukkit.julysafe.core.entity.filter

import com.github.julyss2019.bukkit.voidframework.yaml.Section

abstract class RegexesEntityFilter : BaseEntityFilter() {
    lateinit var regexes: List<Regex>
        private set

    fun matchRegex(string: String?): Boolean {
        if (string == null) {
            return false
        }

        return regexes.any { it.matches(string) }
    }

    override fun setProperties(section: Section) {
        regexes = section.getStringList("regexes").map { it.toRegex() }
    }
}