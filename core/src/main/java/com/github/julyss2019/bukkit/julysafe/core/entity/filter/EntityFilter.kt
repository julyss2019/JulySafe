package com.github.julyss2019.bukkit.julysafe.core.entity.filter

import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.entity.Entity

interface EntityFilter {
    var id: String

    fun filter(entity: Entity): Boolean

    enum class Type(val mappingClass: Class<out EntityFilter>) {
        CLASS(ClassEntityFilter::class.java),
        CUSTOM_NAME(CustomNameEntityFilter::class.java),
        ENUM(EnumEntityFilter::class.java),
        GROOVY(GroovyEntityFilter::class.java),
        METADATA(MetadataEntityFilter::class.java),
        HAS_PASSENGERS(HasPassengersEntityFilter::class.java)
    }

    fun setProperties(section: Section)

    object Parser {
        fun parse(section: Section): EntityFilter {
            return section.getEnum("type", Type::class.java).mappingClass.newInstance()
                .apply {
                    id = section.name
                    setProperties(section.getSection("properties"))
                }
        }
    }
}