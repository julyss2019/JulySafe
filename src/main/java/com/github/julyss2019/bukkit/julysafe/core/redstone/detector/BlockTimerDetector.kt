package com.github.julyss2019.bukkit.julysafe.core.redstone.detector

import com.github.julyss2019.bukkit.julysafe.core.kotlin.extension.getAsSimpleString
import com.github.julyss2019.bukkit.julysafe.core.redstone.record.TimerRecord
import org.bukkit.Location
import org.bukkit.event.block.BlockRedstoneEvent

class BlockTimerDetector : BaseDetector() {
    private val blockMap = mutableMapOf<Location, TimerRecord>()

    @Suppress("DuplicatedCode")
    override fun detect(event: BlockRedstoneEvent) {
        val block = event.block
        val location = block.location

        if (!blockMap.containsKey(location)) {
            blockMap[location] = TimerRecord(this)
        } else {
            blockMap[location]!!.update()
        }

        val timerRecord = blockMap[location]!!
        val nearbyPlayers = getNearbyPlayers(location)

        if (timerRecord.isBanned()) {
            event.newCurrent = 0

            notifyPlayers(timerRecord, location, nearbyPlayers)
            return
        }

        if (timerRecord.isGap()) {
            return
        }

        timerRecord.addSecond()
        playerDebug(nearbyPlayers, "detector = ${javaClass.simpleName}, block = ${block.getAsSimpleString()}, location = ${location.getAsSimpleString()}, seconds/threshold = ${timerRecord.seconds}/${threshold}.")

        if (timerRecord.seconds == threshold) {
            event.newCurrent = 0
            timerRecord.ban()
            notifyPlayers(timerRecord, location, nearbyPlayers)
            destroyBlockIfNecessary(block)
            debugBlock(block, nearbyPlayers)
        }
    }
}