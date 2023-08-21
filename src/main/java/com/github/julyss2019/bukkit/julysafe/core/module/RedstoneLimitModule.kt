package com.github.julyss2019.bukkit.julysafe.core.module

import com.github.julyss2019.bukkit.julysafe.core.listener.RedstoneLimitListener
import com.github.julyss2019.bukkit.julysafe.core.redstone.detector.Detector
import com.github.julyss2019.bukkit.voidframework.yaml.Section

@Module.YamlSectionId("redstone_limit")
class RedstoneLimitModule : BaseModule() {
    lateinit var detector: Detector
        private set

    override fun setProperties(section: Section) {
        detector = Detector.Parser.parse(this, section)
    }

    override fun onEnable() {
        detector.onEnabled()
        context.registerListener(RedstoneLimitListener(this))
    }
}