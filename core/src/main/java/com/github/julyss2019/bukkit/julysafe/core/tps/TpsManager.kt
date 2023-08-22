package com.github.julyss2019.bukkit.julysafe.core.tps

import com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class TpsManager(private val plugin: com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin) {
    var tps = 0.0
        private set
    val maxSampleCount = 10
    private var lastSample: Long = -1
    private val samples = LinkedList<Double>()

    init {
        object : BukkitRunnable() {
            override fun run() {
                val now = System.nanoTime()

                if (lastSample == -1L) {
                    lastSample = now
                    return
                }

                val timeSpent = now - lastSample

                if (samples.size > maxSampleCount) {
                    samples.remove()
                }

                // 总 tick / seconds = tps（ticks per second，每秒能处理几个tick）
                // 20 tick, 1纳秒 = 1e9秒
                val tps = 20.0 / (timeSpent / 1E9)

                samples.add(tps)
                lastSample = now
            }
        }
    }

    fun getAverageTps(): Double {
        if (samples.size != maxSampleCount) {
            return -1.0
        }

        return samples.average()
    }
}
