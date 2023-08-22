package com.github.julyss2019.bukkit.julysafe.core.util

import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.max

class CooldownTimer {
    companion object {
        private val decimalFormat = DecimalFormat("#.##")
            .apply { roundingMode = RoundingMode.CEILING }

        fun createNewAndStart(cooldownMills: Int): CooldownTimer {
            val cooldownTimer = CooldownTimer()

            cooldownTimer.cooldown = cooldownMills
            cooldownTimer.start()
            return cooldownTimer
        }
    }

    var startTime: Long = -1
        private set
    var cooldown: Int = -1
        private set
    private val started: Boolean
        get() {
            return startTime != -1L
        }

    fun isInCooldown(): Boolean {
        require(started) {
            "not started"
        }

        return getRemainingCooldown() > 0
    }

    /**
     * 获取经过格式化的剩余的冷却时间，以秒为单位
     * 保留 2 位小数，四舍五入
     */
    fun getFormattedRemainingCooldown(): String {
        return decimalFormat.format(getRemainingCooldown() / 1000.0)
    }

    /**
     * 获取剩余的冷却时间，以毫秒为单位
     */
    fun getRemainingCooldown(): Long {
        require(started) {
            "not started"
        }

        return max(0L, cooldown - (System.currentTimeMillis() - startTime))
    }

    fun start() {
        require(!started) {
            "already started"
        }

        startTime = System.currentTimeMillis()
    }
}