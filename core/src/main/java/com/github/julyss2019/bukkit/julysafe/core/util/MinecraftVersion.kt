package com.github.julyss2019.bukkit.julysafe.core.util

import org.bukkit.Bukkit

data class MinecraftVersion(val major: Int, val minor: Int) {
    companion object {
        private val CURRENT_VERSION: MinecraftVersion

        init {
            val version = Bukkit.getBukkitVersion().substringBefore("-")
            val parts = version.split(".").map { it.toIntOrNull() ?: 0 }

            // 旧格式: 1.X.Y (e.g., 1.20.4, 1.21.4) — 跳过前面的 1
            // 新格式: YYYY.X.Y (e.g., 25.01.01) — 年份主导，直接取前两个分量
            CURRENT_VERSION = if (parts.size >= 2 && parts[0] == 1) {
                MinecraftVersion(
                    major = parts.getOrElse(1) { 0 },
                    minor = parts.getOrElse(2) { 0 }
                )
            } else {
                MinecraftVersion(
                    major = parts.getOrElse(0) { 0 },
                    minor = parts.getOrElse(1) { 0 }
                )
            }
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