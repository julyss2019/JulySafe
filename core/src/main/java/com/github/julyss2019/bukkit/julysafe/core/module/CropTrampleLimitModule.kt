package com.github.julyss2019.bukkit.julysafe.core.module

import com.github.julyss2019.bukkit.julysafe.core.listener.CropTrampleLimitListener
import com.github.julyss2019.bukkit.julysafe.core.module.support.WorldSetSupport
import com.github.julyss2019.bukkit.julysafe.core.world.WorldSet

@Module.YamlSectionId("crop_trample_limit")
class CropTrampleLimitModule : BaseModule() , WorldSetSupport{
    override lateinit var worldSet: WorldSet

    override fun onEnable() {
        context.registerListener(CropTrampleLimitListener(this))
    }
}