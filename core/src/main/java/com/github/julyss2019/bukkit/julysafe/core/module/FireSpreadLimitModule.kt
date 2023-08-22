package com.github.julyss2019.bukkit.julysafe.core.module

import com.github.julyss2019.bukkit.julysafe.core.listener.FireSpreadLimitListener
import com.github.julyss2019.bukkit.julysafe.core.module.support.WorldSetSupport
import com.github.julyss2019.bukkit.julysafe.core.world.WorldSet

@Module.YamlSectionId("fire_spread_limit")
class FireSpreadLimitModule : BaseModule(), WorldSetSupport {
    override lateinit var worldSet: WorldSet

    override fun onEnable() {
        context.registerListener(FireSpreadLimitListener(this))
    }
}