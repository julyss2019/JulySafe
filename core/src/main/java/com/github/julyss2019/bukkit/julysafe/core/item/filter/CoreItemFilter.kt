package com.github.julyss2019.bukkit.julysafe.core.item.filter

import com.github.julyss2019.bukkit.julysafe.api.ItemFilter
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.entity.Item

interface CoreItemFilter : ItemFilter {
    var id: String

    enum class Type(val mappingClass: Class<out CoreItemFilter>) {
        DISPLAY_NAME(DisplayNameItemFilter::class.java),
        ENUM(EnumItemFilter::class.java),
        ENCHANTMENT(EnchantmentItemFilter::class.java),
        LORE(LoreItemFilter::class.java),
        GROOVY(GroovyItemFilter::class.java)
    }

    fun setProperties(section: Section)

    object Parser {
        fun parse(section: Section): CoreItemFilter {
            return section.getEnum("type", Type::class.java).mappingClass.newInstance()
                .apply {
                    id = section.name
                    setProperties(section.getSection("properties"))
                }
        }
    }
}