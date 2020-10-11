package com.github.julyss2019.bukkit.plugins.julysafe.tasks;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.AntiEntityFarmLimit;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfig;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfigHelper;
import com.github.julyss2019.mcsp.julylibrary.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class AntiEntityFarmTask extends BukkitRunnable {
    private final JulySafe plugin = JulySafe.getInstance();
    private final Logger logger = plugin.getPluginLogger();
    private final MainConfig mainConfig = plugin.getMainConfig();
    private final MainConfigHelper mainConfigHelper = plugin.getMainConfigHelper();

    @Override
    public void run() {
        for (World world : Bukkit.getWorlds()) {
            if (!mainConfigHelper.isAntiEntityFarmWorld(world)) {
                continue;
            }

            for (Chunk chunk : world.getLoadedChunks()) {
                Map<AntiEntityFarmLimit, Set<Entity>> counterMap = new HashMap<>();

                for (Entity entity : chunk.getEntities()) {
                    if (entity instanceof Player) {
                        continue;
                    }

                    AntiEntityFarmLimit limit = mainConfigHelper.matchAntiEntityFarmLimit(entity);

                    if (limit != null) {
                        if (!counterMap.containsKey(limit)) {
                            counterMap.put(limit, new HashSet<>());
                        }

                        counterMap.get(limit).add(entity);
                    }
                }

                for (Map.Entry<AntiEntityFarmLimit, Set<Entity>> entry : counterMap.entrySet()) {
                    AntiEntityFarmLimit limit = entry.getKey();
                    Set<Entity> entities = entry.getValue();

                    if (entities.size() > limit.getThreshold()) {
                        Iterator<Entity> iterator = entities.iterator();

                        for (int i = entities.size(); i > limit.getThreshold(); i--) {
                            Entity entity = iterator.next();

                            logger.debug("[anti_entity_farm] [remove_entity] 实体 = " + entity.getType().name() + ", 位置: " + entity.getLocation() + ".");
                            entity.remove();
                        }
                    }
                }
            }

        }
    }
}
