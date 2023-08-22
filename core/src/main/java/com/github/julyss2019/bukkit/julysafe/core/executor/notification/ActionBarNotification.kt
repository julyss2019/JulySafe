package com.github.julyss2019.bukkit.julysafe.core.executor.notification

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.EnumWrappers
import com.comphenix.protocol.wrappers.WrappedChatComponent
import com.github.julyss2019.bukkit.julysafe.core.util.NmsUtils
import com.github.julyss2019.bukkit.voidframework.yaml.Section
import org.bukkit.Bukkit
import org.bukkit.entity.Player


class ActionBarNotification : Notification {
    private lateinit var message: String

    override fun notifyAll(messageProcessor: Notification.MessageProcessor) {
        Bukkit.getOnlinePlayers().forEach { player: Player? ->
            if (NmsUtils.getVersionAsInt() > 1121) {
                val packetContainer = PacketContainer(PacketType.Play.Server.SET_ACTION_BAR_TEXT)

                packetContainer.chatComponents.write(0, WrappedChatComponent.fromText(messageProcessor.process(message)))
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer)
            } else {
                // https://wiki.vg/index.php?title=Protocol&oldid=14204#Chat_Message_.28clientbound.29
                val packetContainer = PacketContainer(PacketType.Play.Server.CHAT)

                packetContainer.chatComponents.write(0, WrappedChatComponent.fromText(messageProcessor.process(message)))
                packetContainer.chatTypes.write(0, EnumWrappers.ChatType.GAME_INFO)
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer)
            }
        }
    }

    override fun setProperties(section: Section) {
        message = section.getString("message")
    }
}