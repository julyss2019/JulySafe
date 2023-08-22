package com.github.julyss2019.bukkit.julysafe.core.executor.notification

import com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin
import com.github.julyss2019.bukkit.julysafe.core.executor.Executor
import com.github.julyss2019.bukkit.voidframework.yaml.DefaultValue
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar

class BossBarNotification : Notification {
    private lateinit var title: String
    private lateinit var color: BarColor
    private lateinit var style: BarStyle
    private var bossBar: BossBar? = null

    override fun notifyAll(messageProcessor: Notification.MessageProcessor) {
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar("-", color, style)
            com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin.instance.bossBarManager.registerBossBar(bossBar!!)
        }

        bossBar!!.setTitle(messageProcessor.process(title))
    }

    override fun setProperties(section: Section) {
        title = section.getString("title")
        color = section.getEnum("color", BarColor::class.java, DefaultValue.of(BarColor.RED))
        style = section.getEnum("style", BarStyle::class.java, DefaultValue.of(BarStyle.SOLID))
    }

    override fun clear() {
        super.clear()

        com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin.instance.bossBarManager.unregisterBossBar(bossBar!!)
        bossBar = null
    }
}