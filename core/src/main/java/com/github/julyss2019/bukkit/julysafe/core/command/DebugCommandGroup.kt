package com.github.julyss2019.bukkit.julysafe.core.command

import com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin
import com.github.julyss2019.bukkit.julysafe.core.util.MessageUtils
import com.github.julyss2019.bukkit.voidframework.command.CommandGroup
import com.github.julyss2019.bukkit.voidframework.command.annotation.CommandBody
import com.github.julyss2019.bukkit.voidframework.command.annotation.CommandMapping
import org.bukkit.entity.Player

@CommandMapping(value = "debugger") class DebugCommandGroup(private val plugin: com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin) : CommandGroup {
    private val julySafePlayerManager = plugin.julySafePlayerManager

    @CommandBody(value = "debug", description = "开关调试模式") fun debug(player: Player) {
        val julySafePlayer = julySafePlayerManager.getJulySafePlayer(player)

        julySafePlayer.debugEnabled = !julySafePlayer.debugEnabled

        if (julySafePlayer.debugEnabled) {
            MessageUtils.sendMessage(player, "已开启调试模式, 你将收到以下的调试消息: ")
            MessageUtils.sendMessage(player, "- 红石阈值(接近红石通知范围可收到阈值递增提示)")
            MessageUtils.sendMessage(player, "- 实体过滤器(右键实体可显示被哪个过滤器匹配)")
            MessageUtils.sendMessage(player, "- 物品过滤器(丢弃物品可显示被哪个过滤器匹配)")
        } else {
            MessageUtils.sendMessage(player, "已关闭调试模式.")
        }
    }
}