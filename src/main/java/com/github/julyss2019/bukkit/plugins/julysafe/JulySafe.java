package com.github.julyss2019.bukkit.plugins.julysafe;

import com.github.julyss2019.bukkit.plugins.julysafe.bossbar.GlobalBossBarManager;
import com.github.julyss2019.bukkit.plugins.julysafe.command.CustomCommandHandler;
import com.github.julyss2019.bukkit.plugins.julysafe.command.objects.HelperCommand;
import com.github.julyss2019.bukkit.plugins.julysafe.command.objects.PluginCommand;
import com.github.julyss2019.bukkit.plugins.julysafe.config.ConfigLoader;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfig;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfigHelper;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.Lang;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangHelper;
import com.github.julyss2019.bukkit.plugins.julysafe.listeners.*;
import com.github.julyss2019.bukkit.plugins.julysafe.tasks.*;
import com.github.julyss2019.mcsp.julylibrary.JulyLibraryAPI;
import com.github.julyss2019.mcsp.julylibrary.logger.Logger;
import com.github.julyss2019.mcsp.julylibrary.utilv2.PluginUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class JulySafe extends JavaPlugin {
    private static JulySafe instance;
    private TpsTask tpsTask;
    private CleanDropTask cleanDropTask;
    private CleanEntityTask cleanEntityTask;
    private AutoRestartTask autoRestartTask;
    private CustomCommandHandler commandHandler;
    private MainConfig mainConfig;
    private MainConfigHelper mainConfigHelper;
    private ConfigLoader configLoader;
    private Logger pluginLogger;
    private Lang lang;
    private LangHelper langHelper;
    private GlobalBossBarManager globalBossBarManager;

    @Override
    public void onEnable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("JulyLibrary")) {
            throw new RuntimeException("前置插件 JulyLibrary 未加载");
        }

        saveResources();

        instance = this;
        this.pluginLogger = JulyLibraryAPI.getLoggerManager().createLogger(this);
        this.mainConfig = new MainConfig();
        this.configLoader = new ConfigLoader();

        configLoader.load();

        this.commandHandler = new CustomCommandHandler();
        this.mainConfigHelper = new MainConfigHelper();
        this.langHelper = new LangHelper();
        this.globalBossBarManager = new GlobalBossBarManager();

        commandHandler.setCommandFormat("&a[JulySafe] &f/${label} ${arg} - ${desc}");
        registerCommands();
        getCommand("julysafe").setExecutor(commandHandler);
        registerListeners();
        runTasks();
        new Metrics(this, 8485);
        pluginLogger.info("&f插件版本: v" + getDescription().getVersion() + ".");
        pluginLogger.info("&f插件交流群: 1148417878.");
        pluginLogger.info("&f插件初始化完毕.");
    }

    public GlobalBossBarManager getGlobalBossBarManager() {
        return globalBossBarManager;
    }

    public void setLang(@NotNull Lang lang) {
        this.lang = lang;
    }

    private void saveResources() {
        PluginUtil.saveResource(this, "config.yml", new File(getDataFolder(), "config" + File.separator + "config.yml"), false);
        PluginUtil.saveResource(this, "lang.yml", new File(getDataFolder(), "config" + File.separator + "lang.yml"), false);
    }

    public LangHelper getLangHelper() {
        return langHelper;
    }

    public Lang getLang() {
        return lang;
    }

    @Override
    public void onDisable() {
        if (cleanEntityTask != null) {
            cleanEntityTask.onDisabled();
        }

        if (cleanDropTask != null) {
            cleanDropTask.onDisabled();
        }
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
        configLoader.load();
        HandlerList.unregisterAll(this);
        Bukkit.getScheduler().cancelTasks(this);
        runTasks();
        registerListeners();
    }

    private void runTasks() {
        (this.tpsTask = new TpsTask()).runTaskTimer(this, 0L, 20L);

        if (mainConfig.isCleanDropEnabled()) {
            (this.cleanDropTask = new CleanDropTask()).runTaskTimer(this, 0L, 20L);
        }

        if (mainConfig.isCleanEntityEnabled()) {
            (this.cleanEntityTask = new CleanEntityTask()).runTaskTimer(this, 0L, 20L);
        }

        if (mainConfig.isAntiEntityFarmEnabled()) {
            new AntiEntityFarmTask().runTaskTimer(this, 0L, mainConfig.getAntiEntityFarmInterval() * 20L);
        }

        if (mainConfig.isAntiIllegalPlayerEnabled()) {
            new AntiIllegalPlayerTask().runTaskTimer(this, 0L, 20L);
        }

        if (mainConfig.isAutoRestartEnabled()) {
            (this.autoRestartTask = new AutoRestartTask()).runTaskTimer(this, 0L, 20L);
        }
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        if (mainConfig.isQucikshopBugFixEnabled()) {
            pluginManager.registerEvents(new QuickShopBugFixListener(), this);
        }

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
    }
}
