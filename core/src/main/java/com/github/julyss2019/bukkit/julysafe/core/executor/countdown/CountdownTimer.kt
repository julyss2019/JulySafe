package com.github.julyss2019.bukkit.julysafe.core.executor.countdown

import com.github.julyss2019.bukkit.julysafe.core.executor.notification.Notification
import com.github.julyss2019.bukkit.voidframework.yaml.Section

class CountdownTimer(val seconds: List<Int>, val notification: Notification) {
    val maxSecond = seconds.max()

    object SecondsParser {
        fun parse(expression: String): List<Int> {
            val seconds = mutableListOf<Int>()

            expression.split(",").forEach {
                val rangeArray = it.split("-")

                if (rangeArray.size == 1) {
                    seconds.add(rangeArray[0].toInt())
                } else {
                    for (i in rangeArray[0].toInt()..rangeArray[1].toInt()) {
                        seconds.add(i)
                    }
                }
            }

            return seconds
        }
    }

    object Parser {
        fun parse(section: Section): CountdownTimer {
            return CountdownTimer(
                seconds = SecondsParser.parse(section.getString("seconds")),
                notification = Notification.Parser.parse(section.getSection("notification"))
            )
        }
    }
}