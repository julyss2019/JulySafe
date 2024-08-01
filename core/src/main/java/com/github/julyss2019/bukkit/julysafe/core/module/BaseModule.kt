package com.github.julyss2019.bukkit.julysafe.core.module

import com.github.julyss2019.bukkit.julysafe.core.locale.LocaleResource

abstract class BaseModule : Module {
    override lateinit var yamlId: String
    override lateinit var context: Module.Context
    override var enabled: Boolean = false
    override val name: String = javaClass.simpleName

    fun getLocalResource(): LocaleResource {
        return context.plugin.localeResource.getLocalResource(yamlId)
    }

    fun debug(message: String) {
        context.plugin.pluginLogger.debug("[$name] $message")
    }

    fun info(message: String) {
        context.plugin.pluginLogger.info("[$name] $message")
    }
}