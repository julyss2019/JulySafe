package com.github.julyss2019.bukkit.julysafe.core.util

import org.bukkit.Bukkit

object NmsUtils {
    fun getVersion(): String {
        return Bukkit.getServer().javaClass.`package`.name.let {
            it.substring(it.lastIndexOf(".") + 1)
        }
    }

    fun getVersionAsInt(): Int {
        val version = getVersion()
        val array = version.split("_")

        return (array[0].run { substring(1, length) } + array[1] + array[2].run { substring(1, length) }).toInt()
    }
}