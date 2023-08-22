package com.github.julyss2019.bukkit.julysafe.core.module.support

import com.github.julyss2019.bukkit.julysafe.core.executor.Executor
import com.github.julyss2019.bukkit.julysafe.core.module.Module

interface ExecutorSupport : Module {
    var executor: Executor
}