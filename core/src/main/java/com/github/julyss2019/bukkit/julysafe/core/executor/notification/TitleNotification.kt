package com.github.julyss2019.bukkit.julysafe.core.executor.notification

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.EnumWrappers
import com.comphenix.protocol.wrappers.WrappedChatComponent
import com.github.julyss2019.bukkit.julysafe.core.executor.notification.message.processor.MessageProcessor
import com.github.julyss2019.bukkit.julysafe.core.util.NmsUtils
import com.github.julyss2019.bukkit.voidframework.text.Texts
import com.github.julyss2019.bukkit.voidframework.yaml.DefaultValue
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class TitleNotification : Notification {
    companion object {
        fun sendProtocolTitle(
            player: Player,
            isBigTitle: Boolean,
            content: String,
            fadeIn: Int,
            stay: Int,
            fadeOut: Int
        ) {
            val packetContainer = PacketContainer(PacketType.Play.Server.TITLE)

            packetContainer.titleActions.write(
                0,
                if (isBigTitle) EnumWrappers.TitleAction.TITLE else EnumWrappers.TitleAction.SUBTITLE
            )
            packetContainer.chatComponents.write(0, WrappedChatComponent.fromText(Texts.getColoredText(content)))
            packetContainer.integers.write(0, fadeIn)
            packetContainer.integers.write(1, stay)
            packetContainer.integers.write(2, fadeOut)
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer)
        }
    }


    private lateinit var title: String
    private lateinit var subtitle: String
    private var fadeIn: Int = -1
    private var stay: Int = -1
    private var fadeOut: Int = -1
    override fun notifyCountdown(messageProcessor: MessageProcessor, currentCountdown: Int, maxCountdown: Int) {
        notifyCompleted(messageProcessor)
    }

    override fun notifyCompleted(messageProcessor: MessageProcessor) {
        Bukkit.getOnlinePlayers().forEach {
            if (NmsUtils.getVersionAsInt() >= 1120) {
                it.sendTitle(
                    messageProcessor.process(title),
                    messageProcessor.process(subtitle),
                    fadeIn, stay, fadeOut
                )
            } else {
                sendProtocolTitle(
                    it,
                    true,
                    Texts.getColoredText(messageProcessor.process(title)),
                    fadeIn,
                    stay,
                    fadeOut
                )
                sendProtocolTitle(
                    it,
                    false,
                    Texts.getColoredText(messageProcessor.process(subtitle)),
                    fadeIn,
                    stay,
                    fadeOut
                )
            }
        }
    }

    override fun setProperties(section: Section) {
        title = section.getString("title")
        subtitle = section.getString("subtitle")
        fadeIn = section.getInt("fade_in", DefaultValue.of(20))
        stay = section.getInt("stay", DefaultValue.of(20))
        fadeOut = section.getInt("fade_out", DefaultValue.of(20))
    }
}