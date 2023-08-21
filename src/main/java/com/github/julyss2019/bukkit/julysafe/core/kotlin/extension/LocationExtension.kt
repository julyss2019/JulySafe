package com.github.julyss2019.bukkit.julysafe.core.kotlin.extension

import org.bukkit.Location

internal fun Location.getAsSimpleString(): String {
    return "${this.world!!.name}(${this.x}, ${this.y}, ${this.z}, ${this.yaw}, ${this.pitch})"
}