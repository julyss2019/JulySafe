package com.github.julyss2019.bukkit.julysafe.core.redstone.detector

import com.github.julyss2019.bukkit.julysafe.core.module.RedstoneLimitModule
import com.github.julyss2019.bukkit.julysafe.core.world.WorldSet
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.Material
import org.bukkit.event.block.BlockRedstoneEvent

interface Detector {
    enum class Type(val mappingClass: Class<out Detector>) {
        BLOCK_COUNTER(BlockCounterDetector::class.java),
        CHUNK_COUNTER(ChunkCounterDetector::class.java),
        BLOCK_TIMER(BlockTimerDetector::class.java),
        CHUNK_TIMER(ChunkTimerDetector::class.java)
    }

    class NotifyPlayerRange(val x: Double, val y: Double, val z: Double)

    object Parser {
        fun parse(module: RedstoneLimitModule, section: Section): Detector {
            val instance = section.getEnum("detector_type", Type::class.java).mappingClass.newInstance()

            instance.run {
                blockWhitelist = section.getEnumSet("block_whitelist", Material::class.java).toList()
                resetPeriod = section.getInt("reset_period")
                threshold = section.getInt("threshold")
                banDuration = section.getInt("ban_duration")
                notifyPlayerInterval = section.getInt("notify_player_interval")
                worldSet = WorldSet.Parser.parse(section.getSection("world_set"))

                val rangeExpression = section.getString("notify_player_range")

                try {
                    val array = rangeExpression.split(",")

                    notifyPlayerRange = NotifyPlayerRange(array[0].toDouble(), array[1].toDouble(), array[2].toDouble())
                } catch (ex: NumberFormatException) {
                    throw RuntimeException("exists invalid number in $rangeExpression", ex)
                } catch (ex: ArrayIndexOutOfBoundsException) {
                    throw RuntimeException("invalid array length in $rangeExpression")
                }

                destroyBlock = section.getBoolean("destroy_block")
            }
            instance.module = module

            return instance
        }
    }

    var module: RedstoneLimitModule
    var worldSet: WorldSet
    var blockWhitelist: List<Material>
    var resetPeriod: Int
    var threshold: Int
    var banDuration: Int
    var notifyPlayerInterval: Int
    var notifyPlayerRange: NotifyPlayerRange
    var destroyBlock: Boolean

    fun onEnabled()

    fun detect(event: BlockRedstoneEvent)
}