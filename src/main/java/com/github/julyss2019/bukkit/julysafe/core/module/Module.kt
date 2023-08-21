package com.github.julyss2019.bukkit.julysafe.core.module

import com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin
import com.github.julyss2019.bukkit.julysafe.core.entity.EntitySet
import com.github.julyss2019.bukkit.julysafe.core.executor.Executor
import com.github.julyss2019.bukkit.julysafe.core.item.ItemSet
import com.github.julyss2019.bukkit.julysafe.core.module.support.*
import com.github.julyss2019.bukkit.julysafe.core.world.WorldSet
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitTask

interface Module {
    annotation class YamlSectionId(val value: String)

    annotation class AutoInject

    var yamlId: String
    var enabled: Boolean
    var context: Context
    val name: String

    fun setProperties(section: Section) {}

    fun onEnable() {}

    class Context(val plugin: JulySafePlugin) {
        private val mutableRunningTasks = mutableListOf<BukkitTask>()
        private val registeredListeners = mutableListOf<Listener>()

        val runningTasks: List<BukkitTask>
            get() {
                return mutableRunningTasks
            }

        fun cancelRunningTasks() {
            mutableRunningTasks.forEach {
                it.cancel()
            }
        }

        fun addRunningTask(bukkitTask: BukkitTask) {
            mutableRunningTasks.add(bukkitTask)
        }

        fun unregisterListeners() {
            registeredListeners.forEach {
                HandlerList.unregisterAll(it)
            }
        }

        fun registerListener(listener: Listener) {
            Bukkit.getPluginManager().registerEvents(listener, plugin)
            registeredListeners.add(listener)
        }
    }

    companion object {
        private val moduleClasses = mutableListOf<Class<out Module>>()

        init {
            registerModuleClass(AutoRestartModule::class.java)

            registerModuleClass(DropCleanModule::class.java)

            registerModuleClass(EntityExplodeLimitModule::class.java)
            registerModuleClass(ChunkEntityLimitModule::class.java)
            registerModuleClass(EntityCleanModule::class.java)
            registerModuleClass(EntitySpawnLimitModule::class.java)

            registerModuleClass(FireSpreadLimitModule::class.java)
            registerModuleClass(CropTrampleLimitModule::class.java)

            registerModuleClass(CommandBlacklistModule::class.java)
            registerModuleClass(CommandSpamLimitModule::class.java)

            registerModuleClass(ChatSpamLimitModule::class.java)
            registerModuleClass(ChatBlacklistModule::class.java)

            registerModuleClass(BlockExplodeLimitModule::class.java)
            registerModuleClass(RedstoneLimitModule::class.java)

            registerModuleClass(IllegalPlayerLimitModule::class.java)
            registerModuleClass(PlayerDropRecordModule::class.java)
            registerModuleClass(PlayerPickupRecordModule::class.java)
        }

        fun getModuleClasses(): List<Class<out Module>> {
            return moduleClasses.toList()
        }

        private fun registerModuleClass(moduleClass: Class<out Module>) {
            require(!moduleClasses.contains(moduleClass)) {
                "already registered"
            }

            moduleClasses.add(moduleClass)
        }
    }

    object Loader {
        fun load(section: Section, moduleClass: Class<out Module>): Module {
            val inst = moduleClass.newInstance() as Module

            inst.enabled = section.getBoolean("enabled")
            inst.yamlId = section.name

            if (!inst.enabled) {
                return inst
            }

            inst.setProperties(section)

            if (WorldSetSupport::class.java.isAssignableFrom(moduleClass)) {
                (inst as WorldSetSupport).worldSet = section.getSection("world_set").let { WorldSet.Parser.parse(it) }
            }

            if (ItemSetSupport::class.java.isAssignableFrom(moduleClass)) {
                (inst as ItemSetSupport).itemSet = section.getSection("item_set").let { ItemSet.Parser.parse(it) }
            }

            if (EntitySetSupport::class.java.isAssignableFrom(moduleClass)) {
                (inst as EntitySetSupport).entitySet = section.getSection("entity_set").let { EntitySet.Parser.parse(it) }
            }

            if (ExecutorSupport::class.java.isAssignableFrom(moduleClass)) {
                (inst as ExecutorSupport).executor = section.getSection("executor").let { Executor.Parser.parse(it) }
            }

            return inst
        }
    }
}