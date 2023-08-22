package com.github.julyss2019.bukkit.julysafe.core.task

import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getNameAndUuid
import com.github.julyss2019.bukkit.julysafe.core.module.IllegalPlayerLimitModule
import com.github.julyss2019.bukkit.voidframework.text.Texts
import org.bukkit.BanList
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class IllegalPlayerLimitTask(private val module: IllegalPlayerLimitModule) : BukkitRunnable() {
    private val localeResource = module.getLocalResource()

    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            if (!module.creativeModeWhitelist.contains(player.name) && player.gameMode == GameMode.CREATIVE) {
                player.gameMode = GameMode.SURVIVAL
                banForeverAndKick(player, localeResource.getString("illegal_creative_mode_banned"))
                module.info("ban illegal creative player, player = ${player.getNameAndUuid()}")
            }

            if (!module.opWhitelist.contains(player.name) && player.isOp) {
                player.isOp = false
                banForeverAndKick(player, localeResource.getString("illegal_op_banned"))
                module.info("ban illegal op, player = ${player.getNameAndUuid()}")
            }
        }
    }

    private fun banForeverAndKick(player: Player, reason: String) {
        Bukkit.getBanList(BanList.Type.NAME).addBan(player.name, Texts.getColoredText(reason), null, "JulySafe")
        player.kickPlayer(Texts.getColoredText(reason))
    }
}
