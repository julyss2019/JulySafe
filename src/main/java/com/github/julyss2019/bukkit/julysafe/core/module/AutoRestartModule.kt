package com.github.julyss2019.bukkit.julysafe.core.module

import com.github.julyss2019.bukkit.julysafe.core.executor.Executor
import com.github.julyss2019.bukkit.julysafe.core.module.support.ExecutorSupport
import com.github.julyss2019.bukkit.voidframework.text.Texts
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.Bukkit

@Module.YamlSectionId("auto_restart")
class AutoRestartModule : BaseModule(), ExecutorSupport {
    override lateinit var executor: Executor

    lateinit var beforeCommands: List<String>
        private set
    var kickAllBeforeRestart: Boolean = false
        private set

    override fun setProperties(section: Section) {
        beforeCommands = section.getStringList("before_restart_commands")
        kickAllBeforeRestart = section.getBoolean("kick_all_before_restart")
    }

    override fun onEnable() {
        executor.task = object : Executor.Task {
            override fun run() {
                if (kickAllBeforeRestart) {
                    Bukkit.getOnlinePlayers().forEach {
                        it.kickPlayer(Texts.getColoredText(getLocalResource().getString("kick_all")))
                    }
                }

                beforeCommands.forEach { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), it) }
                Bukkit.shutdown()
            }
        }
    }
}