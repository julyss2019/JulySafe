package com.github.julyss2019.bukkit.julysafe.core.locale

import com.github.julyss2019.bukkit.voidframework.yaml.Yaml
import org.bukkit.plugin.Plugin
import java.io.File
import java.util.*

open class LocaleResource private constructor(val locale: Locale, val resourcesFolder: File) {
    interface TextProcessor {
        fun process(text: String): String
    }

    companion object {
        private val DEFAULT_TEXT_PROCESSOR = object : TextProcessor {
            override fun process(text: String): String {
                return text
            }
        }

        /**
         * 获取默认的本土化资源文件夹
         * @param plugin 插件
         * @return
         */
        fun getDefaultLocaleFolder(plugin: Plugin): File {
            return File(plugin.dataFolder, "locales")
        }

        /**
         * 载入一个本土化资源
         * 以 lang_country.yml 载入文件，例：zh_CN.yml
         * @param locale 本土化
         * @param resourceFolder 资源文件夹
         */
        fun fromFolder(
            locale: Locale,
            resourceFolder: File
        ): LocaleResource {
            return LocaleResource(locale, resourceFolder)
        }

        /**
         * 载入一个本土化资源
         * 以插件文件夹下的 locale 文件夹作为资源文件夹
         * @param locale 本土化
         * @param plugin 插件
         */
        fun fromPluginLocaleFolder(
            locale: Locale,
            plugin: Plugin
        ): LocaleResource {
            return LocaleResource(locale, File(plugin.dataFolder, "locales"))
        }
    }

    private val file: File =
        File(resourcesFolder, locale.language + "_" + locale.country.uppercase(Locale.getDefault()) + ".yml")
    private lateinit var yaml: Yaml
    var textProcessor = DEFAULT_TEXT_PROCESSOR

    init {
        load()
    }

    private fun load() {
        if (!file.exists()) {
            throw RuntimeException("locale resource file not found: " + file.absolutePath)
        }

        yaml = Yaml.fromFile(file)
    }

    open fun reload() {
        load()
    }

    open fun getStringList(key: String): List<String> {
        return yaml.getStringList(key)
            .map { textProcessor.process(it) }
    }

    open fun getOriginalString(key: String) : String {
        return yaml.getString(key)
    }

    open fun getString(key: String): String {
        return yaml.getString(key)
            .let { textProcessor.process(it) }
    }

    open fun getLocalResource(key: String): LocaleResource {
        if (!yaml.contains(key)) {
            throw RuntimeException("locale resource not found: $key")
        }

        return object : LocaleResource(locale, resourcesFolder) {
            val parent = this@LocaleResource

            private fun convertKey(key1: String): String {
                return "$key.$key1"
            }

            override fun getLocalResource(key: String): LocaleResource {
                return parent.getLocalResource(convertKey(key))
            }

            override fun getStringList(key: String): List<String> {
                return parent.getStringList(convertKey(key))
            }

            override fun getString(key: String): String {
                return parent.getString(convertKey(key))
            }

            override fun reload() {
                parent.reload()
            }
        }
    }
}
