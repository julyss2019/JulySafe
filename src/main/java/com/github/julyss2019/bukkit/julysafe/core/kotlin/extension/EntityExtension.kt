package com.github.julyss2019.bukkit.julysafe.core.kotlin.extension

import org.bukkit.entity.Entity

internal fun Entity.getAsSimpleString(): String {
    return "${type.name}(${uniqueId})"
}