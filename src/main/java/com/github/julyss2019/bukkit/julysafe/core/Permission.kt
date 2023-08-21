package com.github.julyss2019.bukkit.julysafe.core

import java.lang.StringBuilder

enum class Permission {
    ALL,
    COMMAND_ALL,
    BYPASS_CHAT_SPAM_LIMIT,
    BYPASS_CHAT_BLACKLIST,
    BYPASS_COMMAND_BLACKLIST,
    BYPASS_COMMAND_SPAM_LIMIT;

    fun toBukkitPermission(): String {
        return "JulySafe.${
            this.name.let {
                val sb = StringBuilder()

                for (s in it.split("_")) {
                    sb.append(s[0].uppercase() + s.substring(1))
                }

                sb
            }
        }"
    }
}