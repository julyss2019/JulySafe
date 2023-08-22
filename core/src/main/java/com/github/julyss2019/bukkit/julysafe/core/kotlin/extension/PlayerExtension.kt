package com.github.julyss2019.bukkit.julysafe.core.kotlin.extension

import org.bukkit.entity.Player

internal fun Player.getNameAndUuid(): String {
    return "${this.name}(${this.uniqueId})"
}