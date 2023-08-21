package com.github.julyss2019.bukkit.julysafe.core.executor

import com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class FixedTimeExecutor : BaseExecutor() {
    companion object {
        private val timePattern = DateTimeFormatter.ofPattern("HH:mm:ss")
    }

    private lateinit var times: List<LocalTime>
    private lateinit var bukkitTask: BukkitTask
    private var lastExecute: LocalTime? = null

    override fun setProperties(section: Section) {
        times = section.getStringList("times")
            .map {
                try {
                    LocalTime.parse(it, timePattern)
                } catch (ex: Exception) {
                    throw RuntimeException("invalid time expression: $it")
                }
            }
    }

    override fun start() {
        val runnable = object : BukkitRunnable() {
            override fun run() {
                // 忽略毫秒
                val now = LocalTime.now().truncatedTo(ChronoUnit.SECONDS)

                times.forEach { localTime ->
                    // 因为扫描频率较高，所以要避免重复秒问题
                    if (lastExecute == now) {
                        return
                    }

                    lastExecute = now

                    val duration = Duration.between(now, localTime)
                    val countdown: Int = duration.seconds.toInt()

                    notifyCountdown(countdown)

                    // 忽略毫秒
                    if (now.truncatedTo(ChronoUnit.SECONDS).equals(localTime)) {
                        // 转为同步线程
                        object : BukkitRunnable() {
                            override fun run() {
                                task.run()
                            }
                        }.runTask(JulySafePlugin.instance)
                    }
                }
            }
        }

        // 50ms 一次，降低扫描间隔以避免跳秒问题
        bukkitTask = runnable.runTaskTimerAsynchronously(module.context.plugin, 0L, 1L)
        module.context.addRunningTask(bukkitTask)
    }

    override fun stop() {
        bukkitTask.cancel()
    }
}