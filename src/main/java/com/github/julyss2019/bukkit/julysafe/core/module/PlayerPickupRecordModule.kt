package com.github.julyss2019.bukkit.julysafe.core.module

import com.github.julyss2019.bukkit.julysafe.core.listener.PlayerPickupRecordListener
import com.github.julyss2019.bukkit.julysafe.core.module.support.WorldSetSupport
import com.github.julyss2019.bukkit.julysafe.core.world.WorldSet

@Module.YamlSectionId("player_pickup_record")
class PlayerPickupRecordModule : BaseModule(), WorldSetSupport {
    override lateinit var worldSet: WorldSet

    override fun onEnable() {
        context.registerListener(PlayerPickupRecordListener(this))
    }
}