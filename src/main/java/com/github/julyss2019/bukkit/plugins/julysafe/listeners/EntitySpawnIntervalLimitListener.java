package com.github.julyss2019.bukkit.plugins.julysafe.listeners;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.mcsp.julylibrary.logger.Logger;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.HashMap;
import java.util.Map;

public class EntitySpawnIntervalLimitListener implements Listener {
    private final JulySafe plugin = JulySafe.getInstance();
    private final Logger logger = plugin.getPluginLogger();
    private final Map<EntityType, Long> lastSpawnMap = new HashMap<>();

    @EventHandler
    public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
/*        EntityType entityType = event.getEntityType();
        long limit = JulySafe.getInstance().getMainConfigHelper().getEntitySpawnIntervalLimit(entityType);

        if (limit == -1) {
            return;
        }

        if (lastSpawnMap.containsKey(entityType) && System.currentTimeMillis() - lastSpawnMap.get(entityType) < limit) {
            event.setCancelled(true);
            logger.debug("[entity_spawn_interval_limit] [禁止出生] 实体 = " + event.getEntity() + ", 出生原因 = " + event.getSpawnReason().name() + ", 位置 = " + event.getEntity().getLocation() + ".");
            return;
        }

        lastSpawnMap.put(entityType, System.currentTimeMillis());*/
    }
}
