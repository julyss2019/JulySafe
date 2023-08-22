package com.github.julyss2019.bukkit.julysafe.core.redstone.detector

import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getAsSimpleString
import com.github.julyss2019.bukkit.julysafe.core.redstone.record.TimerRecord
import org.bukkit.Chunk
import org.bukkit.event.block.BlockRedstoneEvent

class ChunkTimerDetector : BaseDetector() {
    private val chunkMap = mutableMapOf<Chunk, TimerRecord>()

    @Suppress("DuplicatedCode")
    override fun detect(event: BlockRedstoneEvent) {
        val block = event.block
        val location = block.location
        val chunk = location.chunk

        if (!chunkMap.containsKey(chunk)) {
            chunkMap[chunk] = TimerRecord(this)
        } else {
            chunkMap[chunk]!!.update()
        }

        val timerRecord = chunkMap[chunk]!!
        val nearbyPlayers = getNearbyPlayers(location)

        if (timerRecord.isBanned()) {
            event.newCurrent = 0

            notifyPlayers(timerRecord, location, nearbyPlayers)
            return
        }

        if (!timerRecord.isGap()) {
            return
        }

        timerRecord.addSecond()
        playerDebug(nearbyPlayers, "detector = ${javaClass.simpleName}, block = ${block.getAsSimpleString()}, location = ${location.getAsSimpleString()}, chunk = ${chunk.getAsSimpleString()}, seconds/threshold = ${timerRecord.seconds}/${threshold}.")

        if (timerRecord.seconds == threshold) {
            event.newCurrent = 0
            timerRecord.ban()
            notifyPlayers(timerRecord, location, nearbyPlayers)
            destroyBlockIfNecessary(block)
            debugChunk(chunk, nearbyPlayers)
        }
    }
}