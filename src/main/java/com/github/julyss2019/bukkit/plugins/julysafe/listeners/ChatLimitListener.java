package com.github.julyss2019.bukkit.plugins.julysafe.listeners;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfig;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.Lang;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangHelper;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangNode;
import com.github.julyss2019.bukkit.plugins.julysafe.utils.Util;
import com.github.julyss2019.mcsp.julylibrary.text.PlaceholderContainer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatLimitListener implements Listener {
    private final JulySafe plugin = JulySafe.getInstance();
    private final MainConfig mainConfig = plugin.getMainConfig();
    private final LangNode langNode = Lang.getLangNode("chat_limit");
    private final Map<UUID, Long> lastChatMap = new HashMap<>();

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("JulySafe.ChatLimit.Bypass") || player.hasPermission(Util.ADMIN_PER)) {
            return;
        }

        UUID uuid = player.getUniqueId();

        if (lastChatMap.containsKey(uuid) && System.currentTimeMillis() - lastChatMap.get(uuid) < mainConfig.getChatLimitInterval() * 1000L) {
            LangHelper.sendMsg(player, langNode.getString("interval_deny"), new PlaceholderContainer().add("seconds", String.valueOf(mainConfig.getChatLimitInterval() - (System.currentTimeMillis() - lastChatMap.get(uuid)) / 1000L)));
            event.setCancelled(true);
            return;
        }

        String msg = event.getMessage();

        for (String badWordRegex : mainConfig.getChatLimitBadWords()) {
            Pattern pattern = Pattern.compile(badWordRegex);
            Matcher matcher = pattern.matcher(msg);

            if (matcher.find()) {
                if (mainConfig.isChatLimitCancelled()) {
                    event.setCancelled(true);
                    LangHelper.sendMsg(player, langNode.getString("badwords_deny"));
                } else {
                    String finalMsg = msg;

                    for (String badWordRegex1 : mainConfig.getChatLimitBadWords()) {
                        finalMsg = finalMsg.replaceAll(badWordRegex1, mainConfig.getChatLimitReplaceString());
                    }

                    event.setMessage(finalMsg);
                }
            }
        }


        lastChatMap.put(uuid, System.currentTimeMillis());
    }
}
