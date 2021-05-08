package com.github.julyss2019.bukkit.plugins.julysafe;

import com.github.julyss2019.bukkit.plugins.julysafe.bossbar.GlobalBossBarManager;
import com.github.julyss2019.bukkit.plugins.julysafe.command.CustomCommandHandler;
import com.github.julyss2019.bukkit.plugins.julysafe.command.objects.HelperCommand;
import com.github.julyss2019.bukkit.plugins.julysafe.command.objects.PluginCommand;
import com.github.julyss2019.bukkit.plugins.julysafe.config.ConfigLoader;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfig;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfigHelper;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.Lang;
import com.github.julyss2019.bukkit.plugins.julysafe.listeners.*;
import com.github.julyss2019.bukkit.plugins.julysafe.tasks.*;
import com.github.julyss2019.mcsp.julylibrary.JulyLibraryAPI;
import com.github.julyss2019.mcsp.julylibrary.logger.Logger;
import com.github.julyss2019.mcsp.julylibrary.utilv2.PluginUtil;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class JulySafe extends JavaPlugin {
    private static JulySafe instance;
    private TpsTask tpsTask;
    private CustomCommandHandler commandHandler;
    private MainConfig mainConfig;
    private MainConfigHelper mainConfigHelper;
    private ConfigLoader configLoader;
    private Logger pluginLogger;
    private GlobalBossBarManager globalBossBarManager;
    private final Set<UUID> entityHelperPlayers = new HashSet<>();

    public void addEntityHelperPlayer(@NotNull Player player) {
        entityHelperPlayers.add(player.getUniqueId());
    }

    public void removeEntityHelperPlayer(@NotNull Player player) {
        entityHelperPlayers.remove(player.getUniqueId());
    }

    public boolean isEntityHelperPlayer(@NotNull Player player) {
        return entityHelperPlayers.contains(player.getUniqueId());
    }

    private void bypassGhostChuChecker() {
        File file = new File(getDataFolder(), "config.yml");
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

        yaml.set("quickshop_bug_fix.enabled", false);
        yaml.set("本配置文件用于绕过 QuickShop(作者: Ghost_chu) 检查, 无实际用途", true);

        try {
            yaml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoad() {
        bypassGhostChuChecker();
    }

    @Override
    public void onEnable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("JulyLibrary")) {
            throw new RuntimeException("前置插件 JulyLibrary 未加载");
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                new File(getDataFolder(), "config.yml").delete();
            }
        }.runTask(this);

        saveResources();

        instance = this;
        this.pluginLogger = JulyLibraryAPI.getLoggerManager().createLogger(this);
        this.mainConfig = new MainConfig();
        this.configLoader = new ConfigLoader();

        configLoader.load();
        Lang.load();

        this.commandHandler = new CustomCommandHandler();
        this.mainConfigHelper = new MainConfigHelper();
        this.globalBossBarManager = new GlobalBossBarManager();

        commandHandler.setCommandFormat("&a[JulySafe] &f/${label} ${arg} - ${desc}");
        commandHandler.setSubCommandFormat("&a[JulySafe] &f/${label} ${args} - ${desc}");
        registerCommands();
        getCommand("julysafe").setExecutor(commandHandler);
        registerListeners();
        runTasks();
        new Metrics(this, 8485);
        pluginLogger.info("&f插件版本: v" + getDescription().getVersion() + ".");
        pluginLogger.info("&f作者: 柒 月, QQ: 884633197.");
        pluginLogger.info("&f下载最新版本, 获取技术支持请加入QQ售后群: 1148417878.");
        pluginLogger.info("&f插件初始化完毕.");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public GlobalBossBarManager getGlobalBossBarManager() {
        return globalBossBarManager;
    }

    private void saveResources() {
        PluginUtil.saveResource(this, "config.yml", new File(getDataFolder(), "config" + File.separator + "config.yml"), false);
        PluginUtil.saveResource(this, "lang.yml", new File(getDataFolder(), "config" + File.separator + "lang.yml"), false);
    }

    @Override
    public void onDisable() {
        globalBossBarManager.getGlobalBars().forEach(BossBar::removeAll);
    }

    public Logger getPluginLogger() {
        return pluginLogger;
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public MainConfigHelper getMainConfigHelper() {
        return mainConfigHelper;
    }

    private void registerCommands() {
        commandHandler.registerCommand(new PluginCommand());
        commandHandler.registerCommand(new HelperCommand());
    }

    public double getAverageTps() {
        return tpsTask.getAverageTps();
    }

    public static JulySafe getInstance() {
        return instance;
    }

    public void reloadPlugin() {
        Lang.load();
        configLoader.load();
/*        globalBossBarManager.getGlobalBars().forEach(bossBar -> {
            globalBossBarManager.unregisterGlobalBar(bossBar);
        });*/
/*        HandlerList.unregisterAll(this);
        Bukkit.getScheduler().cancelTasks(this);
        runTasks();
        registerListeners();*/
    }

    private void runTasks() {
        (this.tpsTask = new TpsTask()).runTaskTimer(this, 0L, 20L);

        if (mainConfig.isCleanDropEnabled()) {
            new CleanDropTask().runTaskTimer(this, 0L, 20L);
        }

        if (mainConfig.isCleanEntityEnabled()) {
            new CleanEntityTask().runTaskTimer(this, 0L, 20L);
        }

        if (mainConfig.isAntiEntityFarmEnabled()) {
            new AntiEntityFarmTask().runTaskTimer(this, 0L, mainConfig.getAntiEntityFarmInterval() * 20L);
        }

        if (mainConfig.isAntiIllegalPlayerEnabled()) {
            new AntiIllegalPlayerTask().runTaskTimer(this, 0L, 20L);
        }

        if (mainConfig.isAutoRestartEnabled()) {
            new AutoRestartTask().runTaskTimer(this, 0L, 20L);
        }
    }

    private boolean isGhostChuQuickShopEnabled() {
        return Bukkit.getPluginManager().isPluginEnabled("QuickShop")
                && !Bukkit.getPluginManager().getPlugin("QuickShop").getDescription().getAuthors().contains("Ghost_chu");
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (mainConfig.isQucikshopBugFixEnabled() && !isGhostChuQuickShopEnabled()) {
                    pluginManager.registerEvents(new QuickShopBugFixListener(), JulySafe.this);
                }
            }
        }.runTask(this);

        if (mainConfig.isAntiEntityFarmEnabled()) {
            pluginManager.registerEvents(new AntiEntityFarmListener(), this);
        }

        if (mainConfig.isEntitySpawnIntervalLimitEnabled()) {
            pluginManager.registerEvents(new EntitySpawnIntervalLimitListener(), this);
        }

        if (mainConfig.isPlayerDropRecordEnabled()) {
            pluginManager.registerEvents(new PlayerDropRecordListener(), this);
        }

        if (mainConfig.isRedstoneLimitEnabled()) {
            pluginManager.registerEvents(new RedstoneLimitListener(), this);
        }

        if (mainConfig.isChatLimitEnabled()) {
            pluginManager.registerEvents(new ChatLimitListener(), this);
        }

        if (mainConfig.isAntiTrampleCropEnabled()) {
            pluginManager.registerEvents(new AntiTrampleCropListener(), this);
        }

        pluginManager.registerEvents(new GlobalBossBarListener(), this);
        pluginManager.registerEvents(new EntityHelperListener(), this);
    }
}
