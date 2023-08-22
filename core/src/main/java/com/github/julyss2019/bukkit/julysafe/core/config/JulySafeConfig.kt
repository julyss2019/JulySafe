package com.github.julyss2019.bukkit.julysafe.core.config

import com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin
import com.github.julyss2019.bukkit.voidframework.locale.LocaleParser
import com.github.julyss2019.bukkit.voidframework.yaml.Yaml
import java.util.*

class JulySafeConfig(private val plugin: JulySafePlugin) {
    class Scheduler(val delay: Int, val period: Int)

    lateinit var locale: Locale
        private set
    lateinit var scheduler: Scheduler
        private set
    var bStatsEnabled: Boolean = false


    fun reload() {
        load()
    }

    fun load() {
        val yaml = Yaml.fromPluginConfigFile(plugin)

        bStatsEnabled = yaml.getBoolean("bStats_enabled")
        locale = LocaleParser.parse(yaml.getString("locale"))
        scheduler = Scheduler(yaml.getInt("scheduler.delay"),
            yaml.getInt("scheduler.period"))
    }
}