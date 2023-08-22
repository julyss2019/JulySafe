package com.github.julyss2019.bukkit.julysafe.core.executor

import com.github.julyss2019.bukkit.voidframework.yaml.DefaultValue
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

class TimerExecutor : BaseExecutor() {
    private var delay: Int = -1
    private var period: Int = -1
    private lateinit var bukkitTask: BukkitTask
    private var tick = 0

    override fun setProperties(section: Section) {
        delay = section.getInt("delay", DefaultValue.of(0))
        period = section.getInt("period")
    }

    override fun start() {
        bukkitTask = object : BukkitRunnable() {
            override fun run() {
                val countdown = period - tick

                notifyCountdown(countdown)

                if (countdown == 0) {
                    task.run()
                    tick = 0
                } else {
                    tick++
                }
            }
        }.runTaskTimer(module.context.plugin, delay * 20L, 20L)
    }

    override fun stop() {
        bukkitTask.cancel()
    }
}