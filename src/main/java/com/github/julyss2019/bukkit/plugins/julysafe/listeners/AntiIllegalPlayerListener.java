package com.github.julyss2019.bukkit.plugins.julysafe.listeners;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfig;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class AntiIllegalPlayerListener implements Listener {
    private final JulySafe plugin = JulySafe.getInstance();
    private final MainConfig mainConfig = plugin.getMainConfig();

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (player.isOp() && mainConfig.isAntiIllegalPlayerDeopOnQuit()) {
            player.setOp(false);
        }

        if (player.isOp() && mainConfig.isAntiIllegalPlayerSurvivalModeOnQuit()) {
            player.setGameMode(GameMode.SURVIVAL);
        }
    }
}
