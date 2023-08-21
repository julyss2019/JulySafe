package com.github.julyss2019.bukkit.julysafe.core.redstone.record

import com.github.julyss2019.bukkit.julysafe.core.redstone.detector.Detector

interface Record {
    val detector: Detector

    fun getBanExpired(): Long

    fun ban()

    fun isBanned(): Boolean

    fun update()
}