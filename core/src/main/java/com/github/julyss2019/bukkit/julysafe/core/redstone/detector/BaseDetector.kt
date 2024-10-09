package com.github.julyss2019.bukkit.julysafe.core.redstone.detector

import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getAsSimpleString
import com.github.julyss2019.bukkit.julysafe.core.locale.LocaleResource
import com.github.julyss2019.bukkit.julysafe.core.module.RedstoneLimitModule
import com.github.julyss2019.bukkit.julysafe.core.redstone.record.Record
import com.github.julyss2019.bukkit.julysafe.core.world.WorldSet
import com.github.julyss2019.bukkit.voidframework.common.Messages
import com.github.julyss2019.bukkit.voidframework.text.PlaceholderContainer
import com.github.julyss2019.bukkit.voidframework.text.Texts
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import java.text.SimpleDateFormat

abstract class BaseDetector : Detector {
    private lateinit var simpleDateFormat: SimpleDateFormat
    private lateinit var locale: LocaleResource

    override lateinit var module: RedstoneLimitModule
    override lateinit var worldSet: WorldSet
    override lateinit var blockWhitelist: List<Material>
    override lateinit var notifyPlayerRange: Detector.NotifyPlayerRange
    override var resetPeriod: Int = -1
    override var threshold: Int = -1
    override var banDuration: Int = -1
    override var notifyPlayerInterval: Int = -1
    override var destroyBlock: Boolean = false

    override fun onEnabled() {
        locale = module.getLocalResource()
        simpleDateFormat = SimpleDateFormat(locale.getString("date_time_format"))
    }

    protected fun destroyBlockIfNecessary(block: Block) {
        if (destroyBlock) {
            block.breakNaturally()
        }
    }

    protected fun playerDebug(nearbyPlayers: List<Player>, message: String) {
        nearbyPlayers
            .filter { it.isOnline }
            .map { module.context.plugin.julySafePlayerManager.getJulySafePlayer(it) }
            .filter { it.debugEnabled }
            .forEach {
                it.debug(message)
            }
    }

    protected fun debugChunk(chunk: Chunk, nearbyPlayers: List<Player>) {
        module.debug("banned, chunk = ${chunk.getAsSimpleString()}, nearby_players = ${nearbyPlayers.map { it.getAsSimpleString() }}.")
    }

    protected fun debugBlock(block: Block, nearbyPlayers: List<Player>) {
        module.debug("banned, block = ${block.getAsSimpleString()}, location = ${block.location.getAsSimpleString()}, nearby_players = ${nearbyPlayers.map { it.getAsSimpleString() }}.")
    }

    protected fun getNearbyPlayers(location: Location): List<Player> {
        val range = notifyPlayerRange

        return location.world!!.getNearbyEntities(location, range.x, range.y, range.z).filterIsInstance<Player>()
    }

    protected fun notifyPlayers(record: Record, location: Location, nearbyPlayers: List<Player>) {
        nearbyPlayers
            .filter { it.isOnline }
            .forEach {
                val julySafePlayer = module.context.plugin.julySafePlayerManager.getJulySafePlayer(it)
                val now = System.currentTimeMillis()

                if (now - julySafePlayer.lastRedstoneLimitNotify > notifyPlayerInterval * 1000L) {
                    Messages.sendColoredMessage(
                        it, Texts.setPlaceholders(
                            locale.getString("banned"), PlaceholderContainer()
                                .put("x", location.blockX)
                                .put("y", location.blockY)
                                .put("z", location.blockZ)
                                .put("expired", simpleDateFormat.format(record.getBanExpired()))
                        )
                    )
                    julySafePlayer.lastRedstoneLimitNotify = now
                }
            }
    }
}