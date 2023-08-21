package com.github.julyss2019.bukkit.julysafe.core.kotlin.extension

import org.bukkit.Chunk

internal fun Chunk.getAsSimpleString(): String {
    return "Chunk(${this.x}, ${this.z})"
}