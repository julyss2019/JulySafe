package com.github.julyss2019.bukkit.plugins.julysafe.listeners;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfig;
import com.github.julyss2019.mcsp.julylibrary.logger.Logger;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PlayerPickupRecordListener implements Listener {
    private final JulySafe plugin = JulySafe.getInstance();
    private final MainConfig mainConfig = plugin.getMainConfig();
    private final Logger logger = plugin.getPluginLogger();

    @EventHandler
    public void onEntityPickupItemEvent(EntityPickupItemEvent event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        Player player = (Player) entity;
        Item item = event.getItem();

        logger.debug("[drop_record] ID = " + player.getName() + "(" + player.getUniqueId() + "), 物品 = " + item.getItemStack() + ", 位置 = " + item.getLocation() + ".");
    }
}
