package com.github.julyss2019.bukkit.julysafe.core.executor

import com.github.julyss2019.bukkit.voidframework.yaml.Section

class SchedulerExecutor : BaseExecutor() {
    var priority: Int = -1

    override fun setProperties(section: Section) {
        priority = section.getInt("priority")
    }

    override fun start() {
        module.context.plugin.scheduler.addExecutor(this)
    }

    override fun stop() {
        module.context.plugin.scheduler.removeExecutor(this)
    }
}