package com.github.julyss2019.bukkit.julysafe.core.entity.filter

import com.github.julyss2019.bukkit.julysafe.api.EntityFilter
import com.github.julyss2019.bukkit.voidframework.yaml.Section

interface CoreEntityFilter : EntityFilter {
    var id: String

    enum class Type(val mappingClass: Class<out CoreEntityFilter>) {
        CLASS(ClassEntityFilter::class.java),
        CUSTOM_NAME(CustomNameEntityFilter::class.java),
        NAME(NameEntityFilter::class.java),
        ENUM(EnumEntityFilter::class.java),
        GROOVY(GroovyEntityFilter::class.java),
        METADATA(MetadataEntityFilter::class.java),
        HAS_PASSENGERS(HasPassengersEntityFilter::class.java),
        MYTHIC_MOBS(MythicEntityFilter::class.java)
    }

    fun setProperties(section: Section)

    object Parser {
        fun parse(section: Section): CoreEntityFilter {
            return section.getEnum("type", Type::class.java).mappingClass.newInstance()
                .apply {
                    id = section.name
                    setProperties(section.getSection("properties"))
                }
        }
    }
}