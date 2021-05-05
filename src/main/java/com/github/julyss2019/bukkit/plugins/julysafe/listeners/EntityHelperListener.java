package com.github.julyss2019.bukkit.plugins.julysafe.listeners;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class EntityHelperListener implements Listener {
    private final JulySafe plug = JulySafe.getInstance();

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if (plug.isEntityHelperPlayer(player) && event.getHand() == EquipmentSlot.HAND) {
            Entity entity = event.getRightClicked();

            LangHelper.sendMsg(Bukkit.getConsoleSender(), "custom_name = " + entity.getCustomName());
            LangHelper.sendMsg(Bukkit.getConsoleSender(), "name = " + entity.getName());
        }
    }
}
