package com.github.julyss2019.bukkit.julysafe.core.command

import com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin
import com.github.julyss2019.bukkit.julysafe.core.Permission
import com.github.julyss2019.bukkit.julysafe.core.util.MessageUtils
import com.github.julyss2019.bukkit.voidframework.command.CommandGroup
import com.github.julyss2019.bukkit.voidframework.command.annotation.CommandBody
import com.github.julyss2019.bukkit.voidframework.command.annotation.CommandMapping
import org.bukkit.command.CommandSender

@CommandMapping(value = "plugin")
class PluginCommandGroup(private val plugin: JulySafePlugin) : CommandGroup {
    @CommandBody(value = "version", description = "显示插件版本")
    fun version(sender: CommandSender) {
        MessageUtils.sendMessage(sender, "当前版本: ${plugin.description.version}.")
    }

    @CommandBody(value = "reload", description = "重载插件(不建议在生产环境使用)")
    fun reload(sender: CommandSender) {
        plugin.reload()
        MessageUtils.sendMessage(sender, "重载完毕.")
    }

    @CommandBody(value = "listPermissions", description = "列出所有权限")
    fun listPermissions(sender: CommandSender) {
        MessageUtils.sendMessage(sender, "以下: ")
        com.github.julyss2019.bukkit.julysafe.core.Permission.entries.forEach {
            MessageUtils.sendMessage(sender, "- ${it.toBukkitPermission()}")
        }
    }
}