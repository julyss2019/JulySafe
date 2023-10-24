package com.github.julyss2019.bukkit.julysafe.core

import com.github.julyss2019.bukkit.julysafe.core.bossbar.BossBarManager
import com.github.julyss2019.bukkit.julysafe.core.command.PluginCommandGroup
import com.github.julyss2019.bukkit.julysafe.core.command.DebugCommandGroup
import com.github.julyss2019.bukkit.julysafe.core.config.JulySafeConfig
import com.github.julyss2019.bukkit.julysafe.core.listener.BossBarListener
import com.github.julyss2019.bukkit.julysafe.core.listener.EntityAndItemDebugListener
import com.github.julyss2019.bukkit.julysafe.core.locale.LocaleResource
import com.github.julyss2019.bukkit.julysafe.core.module.ModuleManager
import com.github.julyss2019.bukkit.julysafe.core.player.JulySafePlayerManager
import com.github.julyss2019.bukkit.julysafe.core.tps.TpsManager
import com.github.julyss2019.bukkit.julysafe.core.util.FileUtils
import com.github.julyss2019.bukkit.voidframework.VoidFramework
import com.github.julyss2019.bukkit.voidframework.command.annotation.CommandMapping
import com.github.julyss2019.bukkit.voidframework.logging.logger.Logger
import com.github.julyss2019.bukkit.voidframework.text.PlaceholderContainer
import com.github.julyss2019.bukkit.voidframework.text.Texts
import com.void01.bukkit.voidframework.api.common.VoidFramework2
import com.void01.bukkit.voidframework.api.common.library.Library
import com.void01.bukkit.voidframework.api.common.library.Repository
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

@CommandMapping(value = "july-safe", permission = "JulySafe.Command.All")
class JulySafePlugin : JavaPlugin() {
    private val defaultResourcePaths = arrayOf("config.yml", "modules.yml", "locales/zh_CN.yml")

    companion object {
        lateinit var instance: JulySafePlugin
            private set
    }

    lateinit var julySafeConfig: JulySafeConfig
        private set
    lateinit var voidLogger: Logger
        private set
    lateinit var bossBarManager: BossBarManager
        private set
    lateinit var tpsManager: TpsManager
        private set
    lateinit var moduleManager: ModuleManager
        private set
    lateinit var localeResource: LocaleResource
        private set
    lateinit var scheduler: Scheduler
        private set
    lateinit var julySafePlayerManager: JulySafePlayerManager
        private set

    override fun onEnable() {
        VoidFramework2.getLibraryManager().load(
            Library.Builder
                .create()
                .setClassLoaderByBukkitPlugin(this)
                .setDependencyByGradleStyleExpression("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")
                .addRepositories(Repository.ALIYUN, Repository.CENTRAL)
                .addSafeRelocation("_kotlin_", "_com.github.julyss2019.bukkit.julysafe.core.lib.kotlin_")
                .build()
        )

        instance = this
        voidLogger = VoidFramework.getLogManager().createSimpleLogger(this)

        voidLogger.info("插件版本: v${description.version}")

        saveDefaultResources()
        julySafeConfig = JulySafeConfig(this)
        julySafeConfig.load()

        localeResource = LocaleResource.fromPluginLocaleFolder(julySafeConfig.locale, this)
        localeResource.textProcessor = object : LocaleResource.TextProcessor {
            val prefix = localeResource.getString("prefix")

            override fun process(text: String): String {
                return Texts.setPlaceholders(text, PlaceholderContainer().put("prefix", prefix))
            }
        }

        scheduler = createScheduler()

        julySafePlayerManager = JulySafePlayerManager()
        bossBarManager = BossBarManager()
        moduleManager = ModuleManager(this)

        voidLogger.info("正在加载模块: ")
        moduleManager.load()
        scheduler.start()
        registerCommands()

        Bukkit.getPluginManager().run {
            registerEvents(EntityAndItemDebugListener(this@JulySafePlugin), this@JulySafePlugin)
            registerEvents(BossBarListener(this@JulySafePlugin), this@JulySafePlugin)
        }

        if (julySafeConfig.bStatsEnabled) {
            Metrics(this, 8485)
        }

        voidLogger.info("作者: 柒 月, QQ: 884633197.")
        voidLogger.info("JulySafe 付费技术支持 QQ 群: 1148417878.")
        voidLogger.info("插件已加载.")
    }

    override fun onDisable() {
        voidLogger.info("插件已卸载.")
        bossBarManager.unregisterBossBars()
        VoidFramework.getCommandManager().unregisterCommandFrameworks(this)
        VoidFramework.getLogManager().unregisterLoggers(this)
    }

    private fun createScheduler(): Scheduler {
        return Scheduler(this, julySafeConfig.scheduler.delay, julySafeConfig.scheduler.period)
    }

    private fun registerCommands() {
        val commandFramework = VoidFramework.getCommandManager().createCommandFramework(this)

        commandFramework.registerCommandGroup(DebugCommandGroup(this))
        commandFramework.registerCommandGroup(PluginCommandGroup(this))
    }

    fun reload() {
        scheduler.stop()
        saveDefaultResources()
        julySafeConfig.reload()
        localeResource.reload()
        this.scheduler = createScheduler()
        moduleManager.reload()
        scheduler.start()
    }

    fun saveDefaultResources() {
        defaultResourcePaths.forEach {
            FileUtils.writeInputStreamToFile(getResource(it)!!, File(dataFolder, it), false)
        }
    }

    fun checkPermission(sender: CommandSender, permission: Permission): Boolean {
        return sender.hasPermission(permission.toBukkitPermission()) || sender.hasPermission(Permission.ALL.toBukkitPermission())
    }
}