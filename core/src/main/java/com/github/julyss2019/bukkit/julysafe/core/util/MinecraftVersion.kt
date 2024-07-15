package com.github.julyss2019.bukkit.julysafe.core.util

import org.bukkit.Bukkit

data class MinecraftVersion(val major: Int, val minor: Int) {
    companion object {
        private val CURRENT_VERSION: MinecraftVersion

        init {
            val version = Bukkit.getBukkitVersion().substringBefore("-")
            val array = version.split(".")

            CURRENT_VERSION = MinecraftVersion(array[1].toInt(), array.getOrNull(2)?.toInt() ?: 0)
        }

        fun getCurrentVersion(): MinecraftVersion {
            return CURRENT_VERSION
        }
    }

    fun compareVersion(major: Int, minor: Int = 0): Int {
        val tmp = this.major.compareTo(major)

        if (tmp != 0) {
            return tmp
        }

        return this.minor.compareTo(minor)
    }
}