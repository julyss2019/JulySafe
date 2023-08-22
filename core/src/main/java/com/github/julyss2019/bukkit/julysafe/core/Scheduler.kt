package com.github.julyss2019.bukkit.julysafe.core

import com.github.julyss2019.bukkit.julysafe.core.executor.SchedulerExecutor
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

class Scheduler(private val plugin: JulySafePlugin, private var later: Int, private var interval: Int) {
    private val executors = mutableListOf<SchedulerExecutor>()
    private var bukkitTask: BukkitTask? = null

    private var currentExecutorIndex = -1
    private var currentTick = 0

    fun isRunning(): Boolean {
        return bukkitTask != null
    }

    private fun checkRunning() {
        require(isRunning()) {
            "executor is not running"
        }
    }

    private fun checkNotRunning() {
        require(!isRunning()) {
            "executor is running"
        }
    }

    fun getCurrentExecutor(): SchedulerExecutor? {
        checkRunning()

        if (currentExecutorIndex == -1) {
            return null
        }

        return executors[currentExecutorIndex]
    }

    fun clearExecutors() {
        checkNotRunning()

        executors.clear()
    }

    fun removeExecutor(schedulerExecutor: SchedulerExecutor) {
        checkNotRunning()

        executors.remove(schedulerExecutor)
    }

    fun addExecutor(schedulerExecutor: SchedulerExecutor) {
        checkNotRunning()

        executors.add(schedulerExecutor)
        executors.sortByDescending { it.priority } // 优先级降序
    }

    fun nextExecutor() {
        if (executors.isEmpty()) {
            return
        }

        currentExecutorIndex++

        if (currentExecutorIndex == executors.size) {
            currentExecutorIndex = 0
        }
    }

    fun stop() {
        bukkitTask?.cancel()
        currentTick = 0
        currentExecutorIndex = -1
        bukkitTask = null
    }

    fun start() {
        require(!isRunning()) {
            "already running"
        }

        nextExecutor()

        // 无任务
        if (executors.isEmpty()) {
            return
        }

        bukkitTask = object : BukkitRunnable() {
            override fun run() {
                val executor = getCurrentExecutor()!!
                val countdown = interval - currentTick
                val countdownTimer = executor.countdownTimer

                executor.notifyCountdown(countdown)

                if (countdown == 0) {
                    val task = executor.task

                    task.run()
                    countdownTimer?.notification?.clear()
                    nextExecutor()
                    currentTick = 0
                    return
                } else {
                    currentTick++
                }
            }
        }.runTaskTimer(plugin, later * 20L, 20L)
    }
}
