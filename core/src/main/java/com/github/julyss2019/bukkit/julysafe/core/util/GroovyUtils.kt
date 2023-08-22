package com.github.julyss2019.bukkit.julysafe.core.util

import groovy.lang.Binding
import groovy.lang.GroovyShell


object GroovyUtils {
    fun eval(script: String, binding: Binding): Any? {
        val groovyShell = GroovyShell(binding)

        return groovyShell.evaluate(script)
    }
}