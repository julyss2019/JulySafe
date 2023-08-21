package com.github.julyss2019.bukkit.julysafe.core.module

import com.github.julyss2019.bukkit.julysafe.core.listener.BlockExplodeLimitListener
import com.github.julyss2019.bukkit.julysafe.core.module.support.WorldSetSupport
import com.github.julyss2019.bukkit.julysafe.core.world.WorldSet

@Module.YamlSectionId("block_explode_limit")
class BlockExplodeLimitModule : BaseModule(), WorldSetSupport {
    override lateinit var worldSet: WorldSet

    override fun onEnable() {
        context.registerListener(BlockExplodeLimitListener(this))
    }
}