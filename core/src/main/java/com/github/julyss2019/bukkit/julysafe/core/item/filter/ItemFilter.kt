package com.github.julyss2019.bukkit.julysafe.core.item.filter

import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.entity.Item

interface ItemFilter {
    var id: String

    fun filter(item: Item): Boolean

    enum class Type(val mappingClass: Class<out ItemFilter>) {
        DISPLAY_NAME(DisplayNameItemFilter::class.java),
        ENUM(EnumItemFilter::class.java),
        ENCHANTMENT(EnchantmentItemFilter::class.java),
        LORE(LoreItemFilter::class.java),
        GROOVY(GroovyItemFilter::class.java)
    }

    fun setProperties(section: Section)

    object Parser {
        fun parse(section: Section): ItemFilter {
            return section.getEnum("type", Type::class.java).mappingClass.newInstance()
                .apply {
                    id = section.name
                    setProperties(section.getSection("properties"))
                }
        }
    }
}