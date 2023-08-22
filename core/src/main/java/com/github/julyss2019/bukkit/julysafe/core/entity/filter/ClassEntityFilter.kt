package com.github.julyss2019.bukkit.julysafe.core.entity.filter

import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.entity.Entity

class ClassEntityFilter : BaseEntityFilter() {
    private lateinit var classes: List<Class<out Entity>>

    override fun filter(entity: Entity): Boolean {
        return classes.any {
            it.isAssignableFrom(entity.javaClass)
        }
    }

    override fun setProperties(section: Section) {
        classes = section.getStringList("classes").map {
            val clazz: Class<*>

            try {
                clazz = Class.forName(it)
            } catch (ex: Exception) {
                throw RuntimeException("invalid Entity class: $it")
            }

            if (!Entity::class.java.isAssignableFrom(clazz)) {
                throw RuntimeException("not an Entity class: $it")
            }

            clazz as Class<Entity>
        }
    }

    override fun toString(): String {
        return "ClassEntityFilter(id=$id, classes=$classes)"
    }
}