package com.github.julyss2019.bukkit.plugins.julysafe.listeners;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfig;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.Lang;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangHelper;
import com.github.julyss2019.mcsp.julylibrary.logger.Logger;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class QuickShopBugFixListener implements Listener {
    private final JulySafe plugin = JulySafe.getInstance();
    private final Logger logger = plugin.getPluginLogger();
    private final MainConfig mainConfig = plugin.getMainConfig();
    private final Lang lang = plugin.getLang().getLang("quickshop_bug_fix");
    private final LangHelper langHelper = plugin.getLangHelper();

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Block upBlock = block.getRelative(0, 1, 0); // 上面一个方块

        if (upBlock.getType() != Material.CHEST) {
            return;
        }

        Player player = event.getPlayer();
        Material itemStackType = event.getItemInHand().getType();

        if (itemStackType == Material.HOPPER || (itemStackType == Material.RAIL
                || itemStackType == Material.ACTIVATOR_RAIL
                || itemStackType == Material.DETECTOR_RAIL
                || itemStackType == Material.POWERED_RAIL)) {
            langHelper.sendMsg(player, lang.getString("deny"));
            event.setCancelled(true);
            event.setBuild(false);
            logger.debug("[quickshop_bug_fix] [deny] ID = " + player.getName() + "(" + player.getUniqueId() + "), 位置 = " + player.getLocation() + ".");
        }
    }
}
