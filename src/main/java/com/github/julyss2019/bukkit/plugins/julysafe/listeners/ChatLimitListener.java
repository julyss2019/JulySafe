package com.github.julyss2019.bukkit.plugins.julysafe.listeners;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfig;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.Lang;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangHelper;
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
    private final Lang lang = plugin.getLang().getLang("chat_limit");
    private final LangHelper langHelper = plugin.getLangHelper();
    private final Map<UUID, Long> lastChatMap = new HashMap<>();

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (lastChatMap.containsKey(uuid) && System.currentTimeMillis() - lastChatMap.get(uuid) < mainConfig.getChatLimitInterval() * 1000L) {
            langHelper.sendMsg(player, lang.getString("interval_deny"), new PlaceholderContainer().add("seconds", String.valueOf(mainConfig.getChatLimitInterval() - (System.currentTimeMillis() - lastChatMap.get(uuid)) / 1000L)));
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
                    langHelper.sendMsg(player, lang.getString("badwords_deny"));
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
