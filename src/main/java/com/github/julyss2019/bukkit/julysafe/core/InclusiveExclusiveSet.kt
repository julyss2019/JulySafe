package com.github.julyss2019.bukkit.julysafe.core

interface InclusiveExclusiveSet<T> {
    val includes: List<T>
    val excludes: List<T>
}