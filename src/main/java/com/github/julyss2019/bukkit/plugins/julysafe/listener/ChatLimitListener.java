package com.github.julyss2019.bukkit.plugins.julysafe.listener;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.Lang;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatLimitListener implements Listener {
    private final JulySafe plugin = JulySafe.getInstance();
    private final Lang lang = plugin.getLang();
    private final LangHelper langHelper = plugin.getLangHelper();

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {

    }
}
