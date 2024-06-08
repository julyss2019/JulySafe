package com.github.julyss2019.bukkit.julysafe.core.module

import com.github.julyss2019.bukkit.julysafe.core.command.CommandSet
import com.github.julyss2019.bukkit.julysafe.core.listener.CommandLimitListener
import com.github.julyss2019.bukkit.voidframework.yaml.Section

@Module.YamlSectionId("command_limit")
class CommandLimitModule : BaseModule() {
    lateinit var commandSet: CommandSet
        private set

    override fun setProperties(section: Section) {
        this.commandSet = CommandSet.Parser.parse(section.getSection("command_set"))
    }

    override fun onEnable() {
        context.registerListener(CommandLimitListener(this))
    }
}