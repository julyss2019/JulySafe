package com.github.julyss2019.bukkit.julysafe.core.executor

import com.github.julyss2019.bukkit.julysafe.core.executor.completer.Completer
import com.github.julyss2019.bukkit.julysafe.core.executor.countdown.CountdownTimer
import com.github.julyss2019.bukkit.julysafe.core.executor.notification.Notification
import com.github.julyss2019.bukkit.julysafe.core.module.Module
import com.github.julyss2019.bukkit.voidframework.text.PlaceholderContainer

abstract class BaseExecutor : Executor {
    override var countdownTimer: CountdownTimer? = null
    override var completer: Completer? = null
    override lateinit var task: Executor.Task
    override lateinit var module: Module

    fun notifyCountdown(countdown: Int) {
        countdownTimer?.let {
            if (it.seconds.contains(countdown)) {
                it.notification.notifyAll(Notification.ColoredPlaceholderMessageProcessor(PlaceholderContainer().put("countdown", countdown)))
            }
        }

    }
}