package com.github.julyss2019.bukkit.julysafe.core.entity.filter

import io.lumine.mythic.bukkit.MythicBukkit
import io.lumine.xikage.mythicmobs.MythicMobs
import org.bukkit.entity.Entity

class MythicEntityFilter : RegexesEntityFilter() {
    companion object {
        val isLegacy = try {
            MythicMobs.inst()
            true
        } catch (ex: ClassNotFoundException) {
            try {
                MythicBukkit.inst()
                false
            } catch (ex: ClassNotFoundException) {
                throw UnsupportedOperationException("MythicMobs class not found")
            }
        }
    }

    override fun filter(entity: Entity): Boolean {
        if (isLegacy) {
            val mythicMobInstance: io.lumine.xikage.mythicmobs.mobs.ActiveMob? =
                MythicMobs.inst().apiHelper.getMythicMobInstance(entity)

            return if (mythicMobInstance == null) {
                false
            } else {
                matchRegex(mythicMobInstance.mobType)
            }
        } else {
            val mythicMobInstance: io.lumine.mythic.core.mobs.ActiveMob? =
                MythicBukkit.inst().apiHelper.getMythicMobInstance(entity)

            return if (mythicMobInstance == null) {
                false
            } else {
                matchRegex(mythicMobInstance.mobType)
            }
        }
    }
}