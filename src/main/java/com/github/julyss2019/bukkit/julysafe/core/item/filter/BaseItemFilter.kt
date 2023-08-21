package com.github.julyss2019.bukkit.julysafe.core.item.filter

abstract class BaseItemFilter : ItemFilter {
    // 反射自动注入
    override lateinit var id: String
}