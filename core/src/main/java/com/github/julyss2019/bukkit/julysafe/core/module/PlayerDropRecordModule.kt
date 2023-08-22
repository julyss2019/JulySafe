package com.github.julyss2019.bukkit.julysafe.core.module

import com.github.julyss2019.bukkit.julysafe.core.listener.PlayerDropRecordListener
import com.github.julyss2019.bukkit.julysafe.core.module.support.WorldSetSupport
import com.github.julyss2019.bukkit.julysafe.core.world.WorldSet

@Module.YamlSectionId("player_drop_record")
class PlayerDropRecordModule : BaseModule(), WorldSetSupport {
    override lateinit var worldSet: WorldSet

    override fun onEnable() {
        context.registerListener(PlayerDropRecordListener(this))
    }
}