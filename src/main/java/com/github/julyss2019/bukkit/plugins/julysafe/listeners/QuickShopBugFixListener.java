package com.github.julyss2019.bukkit.plugins.julysafe.listeners;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.Lang;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangHelper;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangNode;
import com.github.julyss2019.mcsp.julylibrary.logger.Logger;
import com.github.julyss2019.mcsp.julylibrary.utilv2.NMSUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class QuickShopBugFixListener implements Listener {
    private final JulySafe plugin = JulySafe.getInstance();
    private final Logger logger = plugin.getPluginLogger();
    private final LangNode langNode = Lang.getLangNode("quickshop_bug_fix");

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Block upBlock = block.getRelative(0, 1, 0); // 上面一个方块

        if (upBlock.getType() != Material.CHEST) {
            return;
        }

        Player player = event.getPlayer();
        Material itemStackType = event.getItemInHand().getType();
        boolean above1_12 = NMSUtil.compareVersion("v1_12_R1") > 0;

        if (itemStackType == Material.HOPPER
                || ((above1_12 && itemStackType == Material.valueOf("RAIL"))
                || ((!above1_12 && itemStackType == Material.valueOf("RAILS")))
                || itemStackType == Material.ACTIVATOR_RAIL
                || itemStackType == Material.DETECTOR_RAIL
                || itemStackType == Material.POWERED_RAIL)) {
            LangHelper.sendMsg(player, langNode.getString("deny"));
            event.setCancelled(true);
            event.setBuild(false);
            logger.debug("[quickshop_bug_fix] [deny] ID = " + player.getName() + "(" + player.getUniqueId() + "), 位置 = " + player.getLocation() + ".");
        }
    }
}
