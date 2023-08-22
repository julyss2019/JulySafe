package com.github.julyss2019.bukkit.julysafe.core.module

import com.github.julyss2019.bukkit.julysafe.core.listener.EntityExplodeLimitListener
import com.github.julyss2019.bukkit.julysafe.core.module.support.WorldSetSupport
import com.github.julyss2019.bukkit.julysafe.core.world.WorldSet

@Module.YamlSectionId("entity_explode_limit")
class EntityExplodeLimitModule : BaseModule(), WorldSetSupport {
    override lateinit var worldSet: WorldSet

    override fun onEnable() {
        context.registerListener(EntityExplodeLimitListener(this))
    }
}