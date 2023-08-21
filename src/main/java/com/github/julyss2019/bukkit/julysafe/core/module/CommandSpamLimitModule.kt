package com.github.julyss2019.bukkit.julysafe.core.module

import com.github.julyss2019.bukkit.julysafe.core.listener.CommandSpamLimitListener
import com.github.julyss2019.bukkit.voidframework.yaml.Section

@Module.YamlSectionId("command_spam_limit")
class CommandSpamLimitModule : BaseModule() {
    var threshold: Int = -1
        private set

    override fun setProperties(section: Section) {
        threshold = section.getInt("threshold")
    }

    override fun onEnable() {
        context.registerListener(CommandSpamLimitListener(this))
    }
}