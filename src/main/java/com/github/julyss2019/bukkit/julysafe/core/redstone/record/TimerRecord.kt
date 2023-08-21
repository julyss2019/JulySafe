package com.github.julyss2019.bukkit.julysafe.core.redstone.record

import com.github.julyss2019.bukkit.julysafe.core.redstone.detector.Detector

class TimerRecord(override val detector: Detector) : BaseRecord(detector) {
    var seconds: Int = 0
        private set
    private var detectBeginning: Long = -1L // 检测开始时间
    private var lastTimer : Long = -1

    init {
        detectBeginning = System.currentTimeMillis()
    }

    // 是否在空档期，即本次秒与上一次秒为同秒
    fun isGap(): Boolean {
        return System.currentTimeMillis() / 1000L == lastTimer / 1000L
    }

    fun addSecond() {
        val now = System.currentTimeMillis()

        if (lastTimer / 1000 == now) {
            return
        }

        seconds += 1
        lastTimer = now
    }

    /**
     * 检测重置周期，若到了周期则重置
     */
    override fun update() {
        // 重置周期
        if (System.currentTimeMillis() - detectBeginning > detector.resetPeriod * 1000L) {
            seconds = 0
            detectBeginning = System.currentTimeMillis()
            lastTimer = 0
        }
    }
}