package com.github.julyss2019.bukkit.julysafe.core.executor

import com.github.julyss2019.bukkit.julysafe.core.executor.completer.Completer
import com.github.julyss2019.bukkit.julysafe.core.executor.countdown.CountdownTimer
import com.github.julyss2019.bukkit.julysafe.core.module.Module
import com.github.julyss2019.bukkit.voidframework.yaml.DefaultValue
import com.github.julyss2019.bukkit.voidframework.yaml.Section


interface Executor {
    var countdownTimer: CountdownTimer?
    var completer: Completer?
    var task: Task
    var module: Module

    fun setProperties(section: Section)

    fun start()

    fun stop()

    enum class Type(val classMapping: Class<out Executor>) {
        TIMER(TimerExecutor::class.java),
        SCHEDULER(SchedulerExecutor::class.java),
        FIXED_TIME(FixedTimeExecutor::class.java)
    }

    interface Task {
        fun run()
    }

    object Parser {
        fun parse(section: Section): Executor {
            val instance = section.getEnum("type", Type::class.java).classMapping.newInstance()

            instance.completer = section.getSection("completer").run {
                if (getBoolean("enabled", DefaultValue.of(true))) {
                    Completer.Parser.parse(this)
                } else {
                    null
                }
            }
            instance.countdownTimer = section.getSection("countdown_timer").run {
                if (getBoolean("enabled", DefaultValue.of(true))) {
                    CountdownTimer.Parser.parse(this)
                } else {
                    null
                }
            }
            instance.setProperties(section.getSection("properties"))
            return instance
        }
    }
}