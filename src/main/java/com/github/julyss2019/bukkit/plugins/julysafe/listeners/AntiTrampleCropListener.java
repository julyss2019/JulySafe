package com.github.julyss2019.bukkit.plugins.julysafe.listeners;

import com.github.julyss2019.mcsp.julylibrary.utilv2.NMSUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class AntiTrampleCropListener implements Listener {
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        Material targetMaterial = NMSUtil.compareVersion("v1_13_R1") >= 0 ? Material.FARMLAND : Material.valueOf("SOIL");

        if (event.getAction() == Action.PHYSICAL &&
                event.getHand() == null && block != null && block.getType() == targetMaterial) {
            event.setCancelled(true);
        }
    }
}
