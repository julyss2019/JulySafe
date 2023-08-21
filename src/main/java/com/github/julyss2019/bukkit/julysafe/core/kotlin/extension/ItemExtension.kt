package com.github.julyss2019.bukkit.julysafe.core.kotlin.extension

import org.bukkit.entity.Item

internal fun Item.getAsSimpleString(): String {
    return "Item(location = ${location.getAsSimpleString()}, item_stack = $itemStack)"
}