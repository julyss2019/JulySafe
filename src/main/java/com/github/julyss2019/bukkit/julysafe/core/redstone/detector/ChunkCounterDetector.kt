package com.github.julyss2019.bukkit.julysafe.core.redstone.detector

import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getAsSimpleString
import com.github.julyss2019.bukkit.julysafe.core.redstone.record.CounterRecord
import org.bukkit.Chunk
import org.bukkit.event.block.BlockRedstoneEvent

class ChunkCounterDetector : BaseDetector() {
    private val chunkMap = mutableMapOf<Chunk, CounterRecord>()

    @Suppress("DuplicatedCode")
    override fun detect(event: BlockRedstoneEvent) {
        val block = event.block
        val location = block.location
        val chunk = location.chunk

        if (!chunkMap.containsKey(chunk)) {
            chunkMap[chunk] = CounterRecord(this)
        } else {
            chunkMap[chunk]!!.update()
        }

        val counterRecord: CounterRecord = chunkMap[chunk]!!
        val nearbyPlayers = getNearbyPlayers(location)

        if (counterRecord.isBanned()) {
            event.newCurrent = 0

            notifyPlayers(counterRecord, location, nearbyPlayers)
            return
        }

        counterRecord.addCount()
        playerDebug(nearbyPlayers, "detector = ${javaClass.simpleName}, block = ${block.getAsSimpleString()}, location = ${location.getAsSimpleString()}, chunk = ${chunk.getAsSimpleString()}, " +
                "count/threshold = ${counterRecord.count}/${threshold}.")

        if (counterRecord.count == threshold) {
            event.newCurrent = 0
            counterRecord.ban()
            notifyPlayers(counterRecord, location, nearbyPlayers)
            destroyBlockIfNecessary(block)
            debugChunk(chunk, nearbyPlayers)
        }
    }
}