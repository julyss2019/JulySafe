package com.github.julyss2019.bukkit.plugins.julysafe.listeners;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.AntiEntityFarmLimit;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfigHelper;
import com.github.julyss2019.mcsp.julylibrary.logger.Logger;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class AntiEntityFarmListener implements Listener {
    private final JulySafe plugin = JulySafe.getInstance();
    private final Logger logger = plugin.getPluginLogger();
    private final MainConfigHelper mainConfigHelper = plugin.getMainConfigHelper();

    @EventHandler
    public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        World world = entity.getWorld();

        if (!mainConfigHelper.isAntiEntityFarmWorld(world)) {
            return;
        }

        AntiEntityFarmLimit antiEntityFarmLimit = mainConfigHelper.matchAntiEntityFarmLimit(event.getEntity());

        if (antiEntityFarmLimit == null) {
            return;
        }

        int counter = 0;

        for (Entity entity1 : entity.getLocation().getChunk().getEntities()) {
            if (antiEntityFarmLimit.getTarget().isTarget(entity1) && ++counter > antiEntityFarmLimit.getThreshold()) {
                event.setCancelled(true);
                logger.debug("[clean_entity] [禁止出生] 实体 = " + entity.getType().name() + ", 位置 = " + entity.getLocation() + ".");
                return;
            }
        }
    }
}
