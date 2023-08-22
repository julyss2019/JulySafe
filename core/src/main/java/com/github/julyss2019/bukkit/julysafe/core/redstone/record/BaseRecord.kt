package com.github.julyss2019.bukkit.julysafe.core.redstone.record

import com.github.julyss2019.bukkit.julysafe.core.redstone.detector.Detector

abstract class BaseRecord(override val detector: Detector) : Record {
    private var banExpired: Long = -1

    override fun ban() {
        banExpired = System.currentTimeMillis() + detector.banDuration * 1000L
    }

    override fun isBanned(): Boolean {
        return System.currentTimeMillis() < banExpired
    }

    override fun getBanExpired(): Long {
        return banExpired
    }
}