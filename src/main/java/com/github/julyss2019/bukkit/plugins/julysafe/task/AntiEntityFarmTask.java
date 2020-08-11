package com.github.julyss2019.bukkit.plugins.julysafe.task;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfigHelper;
import com.github.julyss2019.mcsp.julylibrary.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class AntiEntityFarmTask extends BukkitRunnable {
    private final JulySafe plugin = JulySafe.getInstance();
    private final Logger logger = plugin.getPluginLogger();
    private final MainConfigHelper mainConfigHelper = plugin.getMainConfigHelper();

    @Override
    public void run() {
        for (World world : Bukkit.getWorlds()) {
            if (!mainConfigHelper.isAntiMobFarmWorld(world)) {
                continue;
            }

            for (Chunk chunk : world.getLoadedChunks()) {
                Map<EntityType, Set<Entity>> countMap = new HashMap<>();

                for (Entity entity : chunk.getEntities()) {
                    if (entity instanceof Player) {
                        continue;
                    }

                    EntityType type = entity.getType();

                    if (mainConfigHelper.getAntiMobFarmMobLimit(world, type) == -1) {
                        continue;
                    }

                    if (!countMap.containsKey(type)) {
                        countMap.put(type, new HashSet<>());
                    }

                    Set<Entity> livingEntities = countMap.get(type);

                    livingEntities.add(entity);
                }

                for (Map.Entry<EntityType, Set<Entity>> entry : countMap.entrySet()) {
                    EntityType type = entry.getKey();
                    Set<Entity> livingEntities = entry.getValue();
                    int count = livingEntities.size();
                    int limit = mainConfigHelper.getAntiMobFarmMobLimit(world, type);

                    if (count > limit) {
                        Iterator<Entity> iterator = livingEntities.iterator();

                        for (int i = count; i > limit; i--) {
                            Entity entity = iterator.next();

                            logger.debug("[anti_entity_farm] [删除实体] 实体 = " + entity.getType().name() + ", 位置: " + entity.getLocation() + ".");
                            entity.remove();
                        }
                    }
                }
            }

        }
    }
}
