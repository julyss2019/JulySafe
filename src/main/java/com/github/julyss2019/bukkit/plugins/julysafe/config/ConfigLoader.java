package com.github.julyss2019.bukkit.plugins.julysafe.config;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.mcsp.julylibrary.config.JulyConfig;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigLoader {
    private JulySafe plugin = JulySafe.getInstance();
    private MainConfig mainConfig;

    public ConfigLoader(@NotNull MainConfig mainConfig) {
        this.mainConfig = mainConfig;
    }

    public void load() {
        plugin.reloadConfig();

        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config" + File.separator + "config.yml"));

        JulyConfig.loadConfig(config, mainConfig);

        Map<EntityType, Long> entitySpawnLimitMap = new HashMap<>();

        for (String strEntityType : config.getConfigurationSection("entity_spawn_interval_limit.types").getKeys(false)) {
            EntityType entityType;

            try {
                entityType = EntityType.valueOf(strEntityType);
            } catch (Exception e) {
                throw new RuntimeException("entity_spawn_interval——limit.types." + strEntityType + " 类型异常", e);
            }

            entitySpawnLimitMap.put(entityType, config.getLong("entity_spawn_interval_limit.types." + strEntityType));
        }

        mainConfig.setEntitySpawnIntervalLimitMap(entitySpawnLimitMap);

        Map<String, Map<EntityType, Integer>> antiMobFarmWorldMap = new HashMap<>();

        for (String worldName : config.getConfigurationSection("anti_entity_farm.worlds").getKeys(false)) {
            ConfigurationSection worldSection = config.getConfigurationSection("anti_entity_farm.worlds." + worldName);
            Map<EntityType, Integer> mobAmountMap = new HashMap<>();

            for (String entityTypeStr : worldSection.getKeys(false)) {
                EntityType entityType;

                try {
                    entityType = EntityType.valueOf(entityTypeStr);
                } catch (Exception e) {
                    throw new RuntimeException(worldSection.getCurrentPath() + entityTypeStr + " 类型异常", e);
                }

                if (entityType == EntityType.PLAYER) {
                    throw new RuntimeException(worldSection.getCurrentPath() + entityTypeStr + " 不能使用 PLAYER 类型");
                }

                int count = worldSection.getInt(entityTypeStr);

                if (count < 0) {
                    throw new RuntimeException(worldSection.getCurrentPath() + entityTypeStr + " 必须 >= 0");
                }

                mobAmountMap.put(entityType, count);
            }

            antiMobFarmWorldMap.put(worldName, mobAmountMap);
        }

        mainConfig.setCleanDropFilterMaterials(config.getStringList("clean_drop.filter.materials").stream().map(s -> {
            Material material;

            try {
                material = Material.valueOf(s);
            } catch (Exception e) {
                throw new RuntimeException("clean_drop.filter.materials." + s + " 不合法");
            }

            return material;
        }).collect(Collectors.toSet()));
        mainConfig.setCleanEntityFilterTypes(config.getStringList("clean_entity.filter.types").stream().map(s -> {
            EntityType entityType;

            try {
                entityType = EntityType.valueOf(s);
            } catch (Exception e) {
                throw new RuntimeException("clean_entity.filter.types." + s + " 不合法");
            }

            return entityType;
        }).collect(Collectors.toSet()));
        mainConfig.setAntiEntityFarmWorldMap(antiMobFarmWorldMap);
    }
}
