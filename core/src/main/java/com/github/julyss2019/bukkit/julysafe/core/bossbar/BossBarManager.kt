package com.github.julyss2019.bukkit.julysafe.core.bossbar

import org.bukkit.Bukkit
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player

/**
 * 主要用于管理 BoosBar
 */
class BossBarManager {
    private val bars = mutableListOf<BossBar>()

    fun getBars(): List<BossBar> {
        return bars.toList()
    }

    fun registerBossBar(bossBar: BossBar) {
        require(!bars.contains(bossBar)) {
            "bossbar already registered"
        }

        Bukkit.getOnlinePlayers().forEach { player: Player -> bossBar.addPlayer(player) }
        bars.add(bossBar)
    }

    fun containsBossBar(bossBar: BossBar): Boolean {
        return bars.contains(bossBar)
    }

    fun unregisterBossBars() {
        bars.forEach {
            it.removeAll() // 移除所有玩家
        }
        bars.clear()
    }

    fun unregisterBossBar(bossBar: BossBar) {
        require(bars.contains(bossBar)) {
            "bossbar not registered"
        }

        bossBar.removeAll()
        bars.remove(bossBar)
    }
}
