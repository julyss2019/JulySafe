package com.github.julyss2019.bukkit.julysafe.core.module

import com.github.julyss2019.bukkit.julysafe.core.listener.CommandBlacklistListener
import com.github.julyss2019.bukkit.voidframework.yaml.Section

@Module.YamlSectionId("command_blacklist")
class CommandBlacklistModule : BaseModule() {
    lateinit var blacklistRegexes: List<Regex>
        private set

    override fun setProperties(section: Section) {
        blacklistRegexes = section.getStringList("blacklist")
            .map { it.toRegex() }
    }

    override fun onEnable() {
        context.registerListener(CommandBlacklistListener(this))
    }
}