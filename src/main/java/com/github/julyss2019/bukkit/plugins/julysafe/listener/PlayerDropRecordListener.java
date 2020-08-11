package com.github.julyss2019.bukkit.plugins.julysafe.listener;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfig;
import com.github.julyss2019.mcsp.julylibrary.logger.Logger;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropRecordListener implements Listener {
    private final JulySafe plugin = JulySafe.getInstance();
    private final MainConfig mainConfig = plugin.getMainConfig();
    private final Logger logger = plugin.getPluginLogger();

    @EventHandler
    public void onEntityDropItemEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Item item = event.getItemDrop();

        logger.debug("[drop_record] ID = " + player.getName() + "(" + player.getUniqueId() + "), 物品 = " + item.getItemStack() + ", 位置 = " + item.getLocation() + ".");
    }
}
