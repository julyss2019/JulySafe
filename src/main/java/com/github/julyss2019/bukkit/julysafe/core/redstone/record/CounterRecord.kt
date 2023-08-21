package com.github.julyss2019.bukkit.julysafe.core.redstone.record

import com.github.julyss2019.bukkit.julysafe.core.redstone.detector.Detector

class CounterRecord(override val detector: Detector) : BaseRecord(detector) {
    var count: Int = 0
        private set
    private var detectBeginning: Long = -1L // 检测开始时间

    init {
        detectBeginning = System.currentTimeMillis()
    }

    fun addCount() {
        count += 1
    }

    /**
     * 检测重置周期，若到了周期则重置
     */
    override fun update() {
        // 重置周期
        if (System.currentTimeMillis() - detectBeginning > detector.resetPeriod * 1000L) {
            count = 0
            detectBeginning = System.currentTimeMillis()
        }
    }
}