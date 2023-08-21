package com.github.julyss2019.bukkit.julysafe.core.module

import com.github.julyss2019.bukkit.julysafe.core.listener.ChatBlacklistListener
import com.github.julyss2019.bukkit.voidframework.yaml.Section

@Module.YamlSectionId("chat_blacklist")
class ChatBlacklistModule : BaseModule() {
    lateinit var blacklistRegexes: List<Regex>
        private set
    lateinit var replaceString: String
        private set
    var cancelEvent: Boolean = false
        private set

    override fun setProperties(section: Section) {
        blacklistRegexes = section.getStringList("blacklist").map { it.toRegex() }
        replaceString = section.getString("replace_string")
        cancelEvent = section.getBoolean("cancel_event")
    }

    override fun onEnable() {
        context.registerListener(ChatBlacklistListener(this))
    }
}