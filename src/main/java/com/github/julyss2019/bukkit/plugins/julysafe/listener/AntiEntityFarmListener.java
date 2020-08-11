package com.github.julyss2019.bukkit.plugins.julysafe.listener;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfig;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfigHelper;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class AntiEntityFarmListener implements Listener {
    private final JulySafe plugin = JulySafe.getInstance();
    private final MainConfig mainConfig = plugin.getMainConfig();
    private final MainConfigHelper mainConfigHelper = plugin.getMainConfigHelper();

    @EventHandler
    public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
        LivingEntity livingEntity = event.getEntity();
        EntityType entityType = livingEntity.getType();
        Location location = livingEntity.getLocation();
        World world = location.getWorld();
        int limit = mainConfigHelper.getAntiMobFarmMobLimit(world, entityType);

        if (limit == -1) {
            return;
        }

        int counter = 0;

        for (Entity entity : location.getChunk().getEntities()) {
            if (!(entity instanceof LivingEntity)) {
                continue;
            }

            EntityType entityType1 = entity.getType();

            if (entityType1 == entityType && ++counter > mainConfigHelper.getAntiMobFarmMobLimit(world, entityType)) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
