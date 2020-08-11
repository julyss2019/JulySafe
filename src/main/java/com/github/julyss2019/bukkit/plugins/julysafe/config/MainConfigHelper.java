package com.github.julyss2019.bukkit.plugins.julysafe.config;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Optional;

public class MainConfigHelper {
    private final MainConfig mainConfig = JulySafe.getInstance().getMainConfig();

    public boolean isRedstoneLimitWorld(@NotNull World world) {
        return mainConfig.getRedstoneLimitWorlds().contains("*") || mainConfig.getRedstoneLimitWorlds().contains(world.getName());
    }

    public boolean isAntiMobFarmWorld(@NotNull World world) {
        return mainConfig.getAntiEntityFarmWorldMap().containsKey("*") || mainConfig.getAntiEntityFarmWorldMap().containsKey(world.getName());
    }

    public boolean isCleanDropWorld(@NotNull World world) {
        return mainConfig.getCleanDropWorlds().contains("*") || mainConfig.getCleanDropWorlds().contains(world.getName());
    }

    public boolean isCleanEntityWorld(@NotNull World world) {
        return mainConfig.getCleanEntityWorlds().contains("*") || mainConfig.getCleanEntityWorlds().contains(world.getName());
    }

    /**
     * 得到反动物农场数量限制
     * @param entityType
     * @return
     */
    public int getAntiMobFarmMobLimit(@NotNull World world, @NotNull EntityType entityType) {
        String worldName = world.getName();

        if (!isAntiMobFarmWorld(world)) {
            return -1;
        }

        if (mainConfig.getAntiEntityFarmWorldMap().containsKey(worldName)) {
            return mainConfig.getAntiEntityFarmWorldMap().get(worldName).getOrDefault(entityType, Optional.ofNullable(mainConfig.getAntiEntityFarmWorldMap().get("*")).orElse(new HashMap<>()).getOrDefault(entityType, -1));
        }

        return -1;
    }

    /**
     * 得到生物生成间隔限制
     * @param entityType
     * @return 毫秒
     */
    public long getEntitySpawnIntervalLimit(@NotNull EntityType entityType) {
        return mainConfig.getEntitySpawnIntervalLimitMap().getOrDefault(entityType, -1L);
    }
}
