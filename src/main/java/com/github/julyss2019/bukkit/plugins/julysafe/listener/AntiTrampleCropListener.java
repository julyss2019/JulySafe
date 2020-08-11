package com.github.julyss2019.bukkit.plugins.julysafe.listener;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class AntiTrampleCropListener implements Listener {
    private final JulySafe plugin = JulySafe.getInstance();

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();

        if (block != null && block.getType() == Material.FARMLAND) {
            event.setCancelled(true);
        }
    }
}
