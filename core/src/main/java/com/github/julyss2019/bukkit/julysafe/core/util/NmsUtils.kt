package com.github.julyss2019.bukkit.julysafe.core.util

import org.bukkit.Bukkit

object NmsUtils {
    fun getVersionAsInt(): Int {
        val version = Bukkit.getBukkitVersion().substringBefore("-")
        val array = version.split(".")

        return (array[0] + array[1] + array[2]).toInt()
    }
}