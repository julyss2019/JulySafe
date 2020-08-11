package com.github.julyss2019.bukkit.plugins.julysafe;

import com.github.julyss2019.bukkit.plugins.julysafe.command.CustomCommandHandler;
import com.github.julyss2019.bukkit.plugins.julysafe.command.HelperCommand;
import com.github.julyss2019.bukkit.plugins.julysafe.command.PluginCommand;
import com.github.julyss2019.bukkit.plugins.julysafe.config.ConfigLoader;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfig;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfigHelper;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.Lang;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangHelper;
import com.github.julyss2019.bukkit.plugins.julysafe.listener.*;
import com.github.julyss2019.bukkit.plugins.julysafe.task.*;
import com.github.julyss2019.mcsp.julylibrary.JulyLibraryAPI;
import com.github.julyss2019.mcsp.julylibrary.logger.Logger;
import com.github.julyss2019.mcsp.julylibrary.utils.FileUtil;
import com.github.julyss2019.mcsp.julylibrary.utils.PluginUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class JulySafe extends JavaPlugin {
    private static JulySafe instance;
    public static final String VERSION = "1.0.0";
    private TpsTask tpsTask;
    private CleanDropTask cleanDropTask;
    private CleanEntityTask cleanEntityTask;
    private CustomCommandHandler commandHandler;
    private MainConfig mainConfig;
    private MainConfigHelper mainConfigHelper;
    private ConfigLoader configLoader;
    private Logger pluginLogger;
    private Lang lang;
    private LangHelper langHelper;

    @Override
    public void onEnable() {
        instance = this;

        if (!Bukkit.getPluginManager().isPluginEnabled("JulyLibrary")) {
            throw new RuntimeException("前置插件 JulyLibrary 未加载");
        }

        new Metrics(this, 8485);
        saveResources();

        this.commandHandler = new CustomCommandHandler();
        this.mainConfig = new MainConfig();
        this.mainConfigHelper = new MainConfigHelper();
        this.configLoader = new ConfigLoader(mainConfig);
        this.pluginLogger = JulyLibraryAPI.getLoggerManager().createLogger(this);
        this.lang = new Lang(YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config" + File.separator + "lang.yml")));
        this.langHelper = new LangHelper();

        if (mainConfig.isLogStorageEnabled()) {
            pluginLogger.setConsoleLevel(Logger.Level.INFO);
            pluginLogger.setStorage(new Logger.Storage(getDataFolder(), "${date}.log", mainConfig.getLogStorageFlushInterval()));
        }

        commandHandler.setCommandFormat("&a[JulySafe] &f/${label} ${arg} - ${desc}");

        configLoader.load();
        registerCommands();
        getCommand("julysafe").setExecutor(commandHandler);
        registerListeners();
        runTasks();
        pluginLogger.info("&f插件版本: v" + VERSION + ".");
        pluginLogger.info("&f插件交流群: 1148417878.");
        pluginLogger.info("&f插件初始化完毕.");
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
        cleanEntityTask.onDisabled();
        cleanDropTask.onDisabled();
        HandlerList.unregisterAll(this);
        Bukkit.getScheduler().cancelTasks(this);
        configLoader.load();

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
    }

    private void registerListeners() {
        if (mainConfig.isQucikshopBugFixEnabled()) {
            Bukkit.getPluginManager().registerEvents(new QuickShopBugFixListener(), this);
        }

        if (mainConfig.isAntiEntityFarmEnabled()) {
            Bukkit.getPluginManager().registerEvents(new AntiEntityFarmListener(), this);
        }

        if (mainConfig.isEntitySpawnIntervalLimitEnabled()) {
            Bukkit.getPluginManager().registerEvents(new EntitySpawnIntervalLimitListener(), this);
        }

        if (mainConfig.isPlayerDropRecordEnabled()) {
            Bukkit.getPluginManager().registerEvents(new PlayerDropRecordListener(), this);
        }

        if (mainConfig.isRedstoneLimitEnabled()) {
            Bukkit.getPluginManager().registerEvents(new RedstoneLimitListener(), this);
        }

        if (mainConfig.isChatLimitEnabled()) {
            Bukkit.getPluginManager().registerEvents(new ChatLimitListener(), this);
        }

        if (mainConfig.isAntiTrampleCropEnabled()) {
            Bukkit.getPluginManager().registerEvents(new AntiTrampleCropListener(), this);
        }
    }
}
