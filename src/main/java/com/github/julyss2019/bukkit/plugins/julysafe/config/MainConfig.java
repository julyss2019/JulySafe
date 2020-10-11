package com.github.julyss2019.bukkit.plugins.julysafe.config;

import com.github.julyss2019.bukkit.plugins.julysafe.target.DropTarget;
import com.github.julyss2019.bukkit.plugins.julysafe.target.EntityTarget;
import com.github.julyss2019.mcsp.julylibrary.config.Config;
import com.github.julyss2019.mcsp.julylibrary.utilv2.ValidateUtil;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MainConfig {
    //  ########## log ##########
    @Config(path = "log.storage.enabled")
    private boolean logStorageEnabled;

    @Config(path = "log.storage.flush_interval")
    private int logStorageFlushInterval;
    //  ########## log ##########

    //  ########## player_drop_record ##########
    @Config(path = "player_drop_record.enabled")
    private boolean playerDropRecordEnabled;
    //  ########## player_drop_record ##########

    //  ########## player_pickup_record ##########
    @Config(path = "player_pickup_record.enabled")
    private boolean playerPickupRecordEnabled;
    //  ########## player_pickup_record ##########

    //  ########## anti_entity_farm ##########
    @Config(path = "anti_entity_farm.enabled")
    private boolean antiEntityFarmEnabled;

    @Config(path = "anti_entity_farm.worlds")
    private Set<String> antiEntityFarmWorlds;

    @Config(path = "anti_entity_farm.interval")
    private int antiEntityFarmInterval;
    //  ########## anti_entity_farm ##########

    //  ########## clean_entity ##########
    @Config(path = "clean_entity.enabled")
    private boolean cleanEntityEnabled;

    @Config(path = "clean_entity.bossbar.color")
    private BarColor cleanEntityBossBarColor;

    @Config(path = "clean_entity.bossbar.style")
    private BarStyle cleanEntityBossBarStyle;

    @Config(path = "clean_entity.interval")
    private int cleanEntityInterval;

    @Config(path = "clean_entity.worlds")
    private Set<String> cleanEntityWorlds;

    @Config(path = "clean_entity.countdown_seconds")
    private int cleanEntityCountdownSecond;
    //  ########## clean_entity ##########

    //  ########## clean_drop ##########
    @Config(path = "clean_drop.enabled")
    private boolean cleanDropEnabled;

    @Config(path = "clean_drop.bossbar.color")
    private BarColor cleanDropBossBarColor;

    @Config(path = "clean_drop.bossbar.style")
    private BarStyle cleanDropBossBarStyle;

    @Config(path = "clean_drop.interval")
    private int cleanDropInterval;

    @Config(path = "clean_drop.countdown_seconds")
    private int cleanDropCountdownSecond;

    @Config(path = "clean_drop.worlds")
    private Set<String> cleanDropWorlds;
    //  ########## clean_drop ##########

    //  ########## anti_illegal_player ##########
    @Config(path = "anti_illegal_player.enabled")
    private boolean antiIllegalPlayerEnabled;

    @Config(path = "anti_illegal_player.deop_on_quit")
    private boolean antiIllegalPlayerDeopOnQuit;

    @Config(path = "anti_illegal_player.survival_mode_on_quit")
    private boolean antiIllegalPlayerSurvivalModeOnQuit;

    @Config(path = "anti_illegal_player.creative_whitelist")
    private Set<String> antiIllegalPlayerCreativeWhitelist;

    @Config(path = "anti_illegal_player.op_whitelist")
    private Set<String> antiIllegalPlayerOpWhitelist;
    //  ########## anti_illegal_player ##########

    //  ########## redstone_limit ##########
    @Config(path = "redstone_limit.enabled")
    private boolean redstoneLimitEnabled;

    @Config(path = "redstone_limit.tps")
    private int redstoneLimitTps;

    @Config(path = "redstone_limit.worlds")
    private Set<String> redstoneLimitWorlds;

    @Config(path = "redstone_limit.threshold")
    private int redstoneLimitThreshold;

    @Config(path = "redstone_limit.ban_duration")
    private int redstoneLimitBanDuration;

    @Config(path = "redstone_limit.notify_interval")
    private int redstoneLimitNotifyInterval;

    @Config(path = "redstone_limit.duration")
    private int redstoneLimitDuration;
    //  ########## redstone_limit ##########

    //  ########## anti_trample_crop ##########
    @Config(path = "anti_trample_crop.enabled")
    private boolean antiTrampleCropEnabled;
    //  ########## anti_trample_crop ##########

    //  ########## quickshop_bug_fix ##########
    @Config(path = "quickshop_bug_fix.enabled")
    private boolean qucikshopBugFixEnabled;
    //  ########## quickshop_bug_fix ##########

    //  ########## entity_spawn_interval_limit ##########
    @Config(path = "entity_spawn_interval_limit.enabled")
    private boolean entitySpawnIntervalLimitEnabled;
    //  ########## entity_spawn_interval_limit ##########

    //  ########## chat_limit ##########
    @Config(path = "chat_limit.enabled")
    private boolean chatLimitEnabled;

    @Config(path = "chat_limit.interval")
    private int chatLimitInterval;

    @Config(path = "chat_limit.bad_words")
    private List<String> chatLimitBadWords;

    @Config(path = "chat_limit.replace_string")
    private String chatLimitReplaceString;

    @Config(path = "chat_limit.cancelled")
    private boolean chatLimitCancelled;
    //  ########## chat_limit ##########

    // ########## auto_restart ##########
    @Config(path = "auto_restart.enabled")
    private boolean autoRestartEnabled;

    private Set<Integer> autoRestartTimesSeconds;

    @Config(path = "auto_restart.bossbar.color")
    private BarColor autoRestartBossBarColor;

    @Config(path = "auto_restart.bossbar.style")
    private BarStyle autoRestartBossBarStyle;

    @Config(path = "auto_restart.countdown_seconds")
    private int autoRestartCountdownSeconds;

    @Config(path = "auto_restart.before_commands")
    private Set<String> autoRestartBeforeCommands;
    // ########## auto_restart ##########

    private EntityTarget cleanEntityTarget;
    private DropTarget cleanDropTarget;
    private Set<AntiEntityFarmLimit> antiEntityFarmLimits;

    public BarColor getCleanEntityBossBarColor() {
        return cleanEntityBossBarColor;
    }

    public BarStyle getCleanEntityBossBarStyle() {
        return cleanEntityBossBarStyle;
    }

    public BarColor getCleanDropBossBarColor() {
        return cleanDropBossBarColor;
    }

    public BarStyle getCleanDropBossBarStyle() {
        return cleanDropBossBarStyle;
    }

    public boolean isAutoRestartEnabled() {
        return autoRestartEnabled;
    }

    public BarColor getAutoRestartBossBarColor() {
        return autoRestartBossBarColor;
    }

    public BarStyle getAutoRestartBossBarStyle() {
        return autoRestartBossBarStyle;
    }

    public void setAutoRestartTimesSeconds(@NotNull Set<Integer> autoRestartTimesSeconds) {
        this.autoRestartTimesSeconds = new HashSet<>(ValidateUtil.notNullElement(autoRestartTimesSeconds));
    }

    public Set<Integer> getAutoRestartTimesSeconds() {
        return new HashSet<>(autoRestartTimesSeconds);
    }

    public int getAutoRestartCountdownSeconds() {
        return autoRestartCountdownSeconds;
    }

    public Set<String> getAutoRestartBeforeCommands() {
        return new HashSet<>(autoRestartBeforeCommands);
    }

    public EntityTarget getCleanEntityTarget() {
        return cleanEntityTarget;
    }

    public void setCleanEntityTarget(@NotNull EntityTarget cleanEntityTarget) {
        this.cleanEntityTarget = cleanEntityTarget;
    }

    public DropTarget getCleanDropTarget() {
        return cleanDropTarget;
    }

    public void setCleanDropTarget(@NotNull DropTarget cleanDropTarget) {
        this.cleanDropTarget = cleanDropTarget;
    }

    public Set<AntiEntityFarmLimit> getAntiEntityFarmLimits() {
        return new HashSet<>(antiEntityFarmLimits);
    }

    public void setAntiEntityFarmLimits(@NotNull Set<AntiEntityFarmLimit> antiEntityFarmLimits) {
        this.antiEntityFarmLimits = ValidateUtil.notNullElement(antiEntityFarmLimits);
    }

    public Set<String> getAntiEntityFarmWorlds() {
        return new HashSet<>(antiEntityFarmWorlds);
    }

    public boolean isChatLimitEnabled() {
        return chatLimitEnabled;
    }

    public int getChatLimitInterval() {
        return chatLimitInterval;
    }

    public List<String> getChatLimitBadWords() {
        return new ArrayList<>(chatLimitBadWords);
    }

    public String getChatLimitReplaceString() {
        return chatLimitReplaceString;
    }

    public boolean isChatLimitCancelled() {
        return chatLimitCancelled;
    }

    public boolean isRedstoneLimitEnabled() {
        return redstoneLimitEnabled;
    }

    public boolean isEntitySpawnIntervalLimitEnabled() {
        return entitySpawnIntervalLimitEnabled;
    }

    public boolean isQucikshopBugFixEnabled() {
        return qucikshopBugFixEnabled;
    }

    public boolean isAntiIllegalPlayerSurvivalModeOnQuit() {
        return antiIllegalPlayerSurvivalModeOnQuit;
    }

    public boolean isAntiIllegalPlayerEnabled() {
        return antiIllegalPlayerEnabled;
    }

    public boolean isAntiIllegalPlayerDeopOnQuit() {
        return antiIllegalPlayerDeopOnQuit;
    }

    public boolean isPlayerDropRecordEnabled() {
        return playerDropRecordEnabled;
    }

    public boolean isPlayerPickupRecordEnabled() {
        return playerPickupRecordEnabled;
    }

    public boolean isLogStorageEnabled() {
        return logStorageEnabled;
    }

    public int getLogStorageFlushInterval() {
        return logStorageFlushInterval;
    }

    public boolean isAntiTrampleCropEnabled() {
        return antiTrampleCropEnabled;
    }

    public int getRedstoneLimitNotifyInterval() {
        return redstoneLimitNotifyInterval;
    }

    public int getRedstoneLimitDuration() {
        return redstoneLimitDuration;
    }

    public Set<String> getAntiIllegalPlayerCreativeWhitelist() {
        return new HashSet<>(antiIllegalPlayerCreativeWhitelist);
    }

    public Set<String> getAntiIllegalPlayerOpWhitelist() {
        return new HashSet<>(antiIllegalPlayerOpWhitelist);
    }

    public int getCleanDropCountdownSecond() {
        return cleanDropCountdownSecond;
    }

    public int getCleanEntityCountdownSecond() {
        return cleanEntityCountdownSecond;
    }

    public int getCleanEntityInterval() {
        return cleanEntityInterval;
    }

    public boolean isCleanEntityEnabled() {
        return cleanEntityEnabled;
    }

    public Set<String> getCleanEntityWorlds() {
        return new HashSet<>(cleanEntityWorlds);
    }

    public Set<String> getCleanDropWorlds() {
        return new HashSet<>(cleanDropWorlds);
    }

    public int getCleanDropInterval() {
        return cleanDropInterval;
    }

    public int getAntiEntityFarmInterval() {
        return antiEntityFarmInterval;
    }

    public boolean isAntiEntityFarmEnabled() {
        return antiEntityFarmEnabled;
    }

    public boolean isCleanDropEnabled() {
        return cleanDropEnabled;
    }

    public int getRedstoneLimitTps() {
        return redstoneLimitTps;
    }

    public int getRedstoneLimitThreshold() {
        return redstoneLimitThreshold;
    }

    public int getRedstoneLimitBanDuration() {
        return redstoneLimitBanDuration;
    }

    public Set<String> getRedstoneLimitWorlds() {
        return new HashSet<>(redstoneLimitWorlds);
    }
}
