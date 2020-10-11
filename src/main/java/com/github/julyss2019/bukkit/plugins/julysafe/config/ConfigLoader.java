package com.github.julyss2019.bukkit.plugins.julysafe.config;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.Lang;
import com.github.julyss2019.bukkit.plugins.julysafe.target.DropTarget;
import com.github.julyss2019.bukkit.plugins.julysafe.target.EntityTarget;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.DropMatcher;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.EntityMatcher;
import com.github.julyss2019.mcsp.julylibrary.config.JulyConfig;
import com.github.julyss2019.mcsp.julylibrary.logger.Logger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

public class ConfigLoader {
    private final JulySafe plug = JulySafe.getInstance();
    private final MainConfig mainConfig = plug.getMainConfig();
    private final Logger logger = plug.getPluginLogger();

    public void load() {
        plug.reloadConfig();

        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plug.getDataFolder(), "config" + File.separator + "config.yml"));

        try {
            JulyConfig.loadConfig(config, mainConfig);
        } catch (Exception e) {
            throw new RuntimeException("载入 config.yml 失败", e);
        }

        updateLogger(); // 首先设置 Logger
        plug.setLang(new Lang(YamlConfiguration.loadConfiguration(new File(plug.getDataFolder(), "config" + File.separator + "lang.yml"))));
        setCleanEntityTarget();
        setCleanDropTarget();
        setAntiEntityFarmLimits();
        setAutoRestartTimesSeconds();
    }

    public void updateLogger() {
        if (mainConfig.isLogStorageEnabled()) {
            logger.setStorageLevel(Logger.Level.DEBUG);
            logger.setStorage(new Logger.Storage(new File(plug.getDataFolder(), "logs"), "${date}.log", mainConfig.getLogStorageFlushInterval()));
        }
    }

    public void setAutoRestartTimesSeconds() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plug.getDataFolder(), "config" + File.separator + "config.yml"));
        Set<Integer> results = new HashSet<>();

        for (String expression : config.getStringList("auto_restart.times")) {
            String[] array = expression.split(":");

            if (array.length != 3) {
                throw new RuntimeException("时间格式不合法: " +expression);
            }

            int hours;
            int minutes;
            int seconds;

            try {
                hours = Integer.parseInt(array[0]);
                minutes = Integer.parseInt(array[1]);
                seconds = Integer.parseInt(array[2]);
            }  catch (Exception e) {
                throw new RuntimeException("时间格式不合法: " +expression);
            }

            results.add(hours * 60 * 60 + minutes * 60 + seconds);
        }

        mainConfig.setAutoRestartTimesSeconds(results);
    }

    private void setAntiEntityFarmLimits() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plug.getDataFolder(), "config" + File.separator + "config.yml"));
        ConfigurationSection limitsSection = config.getConfigurationSection("anti_entity_farm.limits");
        Set<AntiEntityFarmLimit> results = new HashSet<>();

        for (String limitName : limitsSection.getKeys(false)) {
            ConfigurationSection limitSection = limitsSection.getConfigurationSection(limitName);

            results.add(new AntiEntityFarmLimit(getEntityTarget(limitSection.getConfigurationSection("target")), limitSection.getInt("threshold")));
        }

        mainConfig.setAntiEntityFarmLimits(results);
    }

    /**
     * 获取 EntityTarget
     * @param section 节点
     * @return
     */
    private EntityTarget getEntityTarget(@NotNull ConfigurationSection section) {
        Set<EntityMatcher> includes = new HashSet<>();
        Set<EntityMatcher> excludes = new HashSet<>();
        ConfigurationSection includeSection = section.getConfigurationSection("includes");
        ConfigurationSection excludeSection = section.getConfigurationSection("excludes");

        for (String name : includeSection.getKeys(false)) {
            includes.add(EntityMatcherParser.parse(includeSection.getConfigurationSection(name)));
        }

        for (String name : excludeSection.getKeys(false)) {
            excludes.add(EntityMatcherParser.parse(excludeSection.getConfigurationSection(name)));
        }

        return new EntityTarget(includes, excludes);
    }

    private void setCleanEntityTarget() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plug.getDataFolder(), "config" + File.separator + "config.yml"));

        mainConfig.setCleanEntityTarget(getEntityTarget(config.getConfigurationSection("clean_entity.target")));
    }

    private void setCleanDropTarget() {
        Set<DropMatcher> includes = new HashSet<>();
        Set<DropMatcher> excludes = new HashSet<>();
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plug.getDataFolder(), "config" + File.separator + "config.yml"));
        ConfigurationSection includeSection = config.getConfigurationSection("clean_drop.target.includes");
        ConfigurationSection excludeSection = config.getConfigurationSection("clean_drop.target.excludes");

        for (String name : includeSection.getKeys(false)) {
            includes.add(DropMatcherParser.parse(includeSection.getConfigurationSection(name)));
        }

        for (String name : excludeSection.getKeys(false)) {
            excludes.add(DropMatcherParser.parse(excludeSection.getConfigurationSection(name)));
        }

        mainConfig.setCleanDropTarget(new DropTarget(includes, excludes));
    }
}
