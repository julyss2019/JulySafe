package com.github.julyss2019.bukkit.julysafe.core.module

import com.github.julyss2019.bukkit.julysafe.core.JulySafePlugin
import com.github.julyss2019.bukkit.julysafe.core.module.support.ExecutorSupport
import com.github.julyss2019.bukkit.voidframework.yaml.Yaml
import java.io.File

class ModuleManager(val plugin: JulySafePlugin) {
    private val moduleMap = mutableMapOf<Class<out Module>, Module>()

    fun reload() {
        getEnabledModules().forEach {
            it.context.run {
                cancelRunningTasks()
                unregisterListeners()
            }

            if (it is ExecutorSupport) {
                it.executor.stop()
            }
        }
        moduleMap.clear()
        load()
    }

    fun load() {
        val yaml = Yaml.fromFile(File(plugin.dataFolder, "modules.yml"))

        Module.getModuleClasses().forEach {
            val sectionId = it.getAnnotation(Module.YamlSectionId::class.java).value
            val module: Module

            try {
                module = Module.Loader.load(yaml.getSection(sectionId), it)

                if (module.enabled) {
                    module.context = Module.Context(plugin)
                    module.onEnable()

                    if (module is ExecutorSupport) {
                        val executor = module.executor

                        executor.module = module
                        executor.start()
                    }

                    plugin.pluginLogger.info("${module.javaClass.simpleName}: 已启用.")
                } else {
                    plugin.pluginLogger.info("${module.javaClass.simpleName}: 未启用.")
                }
            } catch (ex: Exception) {
                throw RuntimeException("an exception occurred while loading module", ex)
            }

            moduleMap[it] = module
        }
    }

    fun getEnabledModules(): List<Module> {
        return getModules().filter { it.enabled }
    }

    fun getModules(): List<Module> {
        return moduleMap.values.toList()
    }

    fun <T : Module> getModuleByClass(moduleClass: Class<T>): T? {
        return moduleMap[moduleClass] as T?
    }
}