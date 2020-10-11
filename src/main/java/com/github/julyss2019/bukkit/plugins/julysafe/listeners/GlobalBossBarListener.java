package com.github.julyss2019.bukkit.plugins.julysafe.listeners;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.bossbar.GlobalBossBarManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GlobalBossBarListener implements Listener {
    private final GlobalBossBarManager globalBossBarManager = JulySafe.getInstance().getGlobalBossBarManager();

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        globalBossBarManager.getGlobalBars().forEach(bossBar -> bossBar.addPlayer(event.getPlayer()));
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        globalBossBarManager.getGlobalBars().forEach(bossBar -> {
            if (bossBar.getPlayers().contains(player))
            bossBar.removePlayer(player);
        });
    }
}
