package com.github.julyss2019.bukkit.julysafe.core.executor.notification

import com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin
import com.github.julyss2019.bukkit.julysafe.core.executor.notification.message.processor.MessageProcessor
import com.github.julyss2019.bukkit.voidframework.yaml.DefaultValue
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar

class BossBarNotification : Notification {
    enum class ProgressType {
        INCREASING, DECREASING
    }

    private lateinit var title: String
    private lateinit var color: BarColor
    private lateinit var style: BarStyle
    private lateinit var progressType: ProgressType
    private var bossBar: BossBar? = null
    override fun notifyCountdown(messageProcessor: MessageProcessor, currentCountdown: Int, maxCountdown: Int) {
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar("-", color, style)
            JulySafePlugin.instance.bossBarManager.registerBossBar(bossBar!!)
        }

        bossBar!!.run {
            title = messageProcessor.process(this@BossBarNotification.title)
            val tmp = currentCountdown / maxCountdown.toDouble()
            progress = if (progressType == ProgressType.DECREASING) tmp else 1 - tmp
        }
    }

    override fun notifyCompleted(messageProcessor: MessageProcessor) {
        throw UnsupportedOperationException()
    }

    override fun setProperties(section: Section) {
        title = section.getString("title")
        color = section.getEnum("color", BarColor::class.java, DefaultValue.of(BarColor.RED))
        style = section.getEnum("style", BarStyle::class.java, DefaultValue.of(BarStyle.SOLID))
        progressType =
            section.getEnum("progress_type", ProgressType::class.java, DefaultValue.of(ProgressType.DECREASING))
    }

    override fun clear() {
        super.clear()

        JulySafePlugin.instance.bossBarManager.unregisterBossBar(bossBar!!)
        bossBar = null
    }
}