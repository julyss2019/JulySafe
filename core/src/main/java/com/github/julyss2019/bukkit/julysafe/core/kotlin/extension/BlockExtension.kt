package com.github.julyss2019.bukkit.julysafe.core.kotlin.extension

import org.bukkit.block.Block

internal fun Block.getAsSimpleString(): String {
    return type.name
}