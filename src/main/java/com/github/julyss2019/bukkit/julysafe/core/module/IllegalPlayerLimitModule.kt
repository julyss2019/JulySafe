package com.github.julyss2019.bukkit.julysafe.core.module

import com.github.julyss2019.bukkit.julysafe.core.listener.IllegalPlayerLimitListener
import com.github.julyss2019.bukkit.julysafe.core.task.IllegalPlayerLimitTask
import com.github.julyss2019.bukkit.voidframework.yaml.Section

@Module.YamlSectionId("illegal_player_limit")
class IllegalPlayerLimitModule : BaseModule() {
    var deopOnQuit: Boolean = false
        private set
    var setSurvivalModeOnQuit: Boolean = false
        private set
    lateinit var creativeModeWhitelist: List<String>
        private set
    lateinit var opWhitelist: List<String>
        private set

    override fun setProperties(section: Section) {
        deopOnQuit = section.getBoolean("deop_on_quit")
        setSurvivalModeOnQuit = section.getBoolean("set_survival_mode_on_quit")
        creativeModeWhitelist = section.getStringList("creative_mode_whitelist")
        opWhitelist = section.getStringList("op_whitelist")
    }

    override fun onEnable() {
        context.addRunningTask(IllegalPlayerLimitTask(this).runTaskTimer(context.plugin, 0L, 20L))
        context.registerListener(IllegalPlayerLimitListener(this))
    }
}