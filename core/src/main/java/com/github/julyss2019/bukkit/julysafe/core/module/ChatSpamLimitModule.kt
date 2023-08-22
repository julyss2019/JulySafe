package com.github.julyss2019.bukkit.julysafe.core.module

import com.github.julyss2019.bukkit.julysafe.core.listener.ChatSpamLimitListener
import com.github.julyss2019.bukkit.voidframework.yaml.Section

@Module.YamlSectionId("chat_spam_limit")
class ChatSpamLimitModule : BaseModule() {
    var threshold: Int = -1
        private set

    override fun setProperties(section: Section) {
        threshold = section.getInt("threshold")
    }

    override fun onEnable() {
        context.registerListener(ChatSpamLimitListener(this))
    }
}