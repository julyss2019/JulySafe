package com.github.julyss2019.bukkit.julysafe.core.redstone.detector

import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getAsSimpleString
import com.github.julyss2019.bukkit.julysafe.core.redstone.record.CounterRecord
import org.bukkit.Location
import org.bukkit.event.block.BlockRedstoneEvent

class BlockCounterDetector : BaseDetector() {
    private val blockMap = mutableMapOf<Location, CounterRecord>()

    @Suppress("DuplicatedCode")
    override fun detect(event: BlockRedstoneEvent) {
        val block = event.block
        val location = block.location

        if (!blockMap.containsKey(location)) {
            blockMap[location] = CounterRecord(this)
        } else {
            blockMap[location]!!.update()
        }

        val counterRecord: CounterRecord = blockMap[location]!!
        val nearbyPlayers = getNearbyPlayers(location)

        if (counterRecord.isBanned()) {
            event.newCurrent = 0

            notifyPlayers(counterRecord, location, nearbyPlayers)
            return
        }

        counterRecord.addCount()
        playerDebug(nearbyPlayers, "detector = ${javaClass.simpleName}, block = ${block.getAsSimpleString()}, location = ${location.getAsSimpleString()}, count/threshold = ${counterRecord.count}/${threshold}.")

        if (counterRecord.count == threshold) {
            event.newCurrent = 0
            counterRecord.ban()
            notifyPlayers(counterRecord, location, nearbyPlayers)
            destroyBlockIfNecessary(block)
            debugBlock(block, nearbyPlayers)
        }
    }
}