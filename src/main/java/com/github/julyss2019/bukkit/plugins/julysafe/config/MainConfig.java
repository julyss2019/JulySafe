package com.github.julyss2019.bukkit.plugins.julysafe.config;

import com.github.julyss2019.mcsp.julylibrary.config.Config;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MainConfig {
    @Config(path = "log.storage.enabled")
    private boolean logStorageEnabled;

    @Config(path = "log.storage.flush_interval")
    private int logStorageFlushInterval;

    @Config(path = "player_drop_record.enabled")
    private boolean playerDropRecordEnabled;

    @Config(path = "player_pickup_record.enabled")
    private boolean playerPickupRecordEnabled;

    @Config(path = "anti_entity_farm.enabled")
    private boolean antiEntityFarmEnabled;

    @Config(path = "anti_entity_farm.interval")
    private int antiEntityFarmInterval;

    @Config(path = "clean_drop.enabled")
    private boolean cleanDropEnabled;

    @Config(path = "clean_drop.interval")
    private int cleanDropInterval;

    @Config(path = "clean_drop.countdown_second")
    private int cleanDropCountdownSecond;

    @Config(path = "clean_drop.filter.lore_items")
    private boolean cleanDropFilterLoreItems;

    @Config(path = "clean_drop.worlds")
    private List<String> cleanDropWorlds;

    @Config(path = "clean_entity.enabled")
    private boolean cleanEntityEnabled;

    @Config(path = "clean_entity.interval")
    private int cleanEntityInterval;

    @Config(path = "clean_entity.worlds")
    private List<String> cleanEntityWorlds;

    @Config(path = "clean_entity.filter.animals")
    private boolean cleanEntityFilterAnimals;

    @Config(path = "clean_entity.countdown_second")
    private int cleanEntityCountdownSecond;

    @Config(path = "clean_entity.filter.npcs")
    private boolean cleanEntityFilterNpcs;

    @Config(path = "clean_entity.filter.golems")
    private boolean cleanEntityFilterGolems;

    @Config(path = "clean_entity.filter.named")
    private boolean cleanEntityFilterNamed;

    @Config(path = "clean_entity.filter.water_mobs")
    private boolean cleanEntityFilterWaterMobs;

    @Config(path = "anti_illegal_player.enabled")
    private boolean antiIllegalPlayerEnabled;

    @Config(path = "anti_illegal_player.deop_on_quit")
    private boolean antiIllegalPlayerDeopOnQuit;

    @Config(path = "anti_illegal_player.survival_mode_on_quit")
    private boolean antiIllegalPlayerSurvivalModeOnQuit;

    @Config(path = "anti_illegal_player.creative_whitelist")
    private List<String> antiIllegalPlayerCreativeWhitelist;

    @Config(path = "anti_illegal_player.op_whitelist")
    private List<String> antiIllegalPlayerOpWhitelist;

    @Config(path = "redstone_limit.enabled")
    private boolean redstoneLimitEnabled;

    @Config(path = "redstone_limit.tps")
    private int redstoneLimitTps;

    @Config(path = "redstone_limit.worlds")
    private List<String> redstoneLimitWorlds;

    @Config(path = "redstone_limit.threshold")
    private int redstoneLimitThreshold;

    @Config(path = "redstone_limit.ban_duration")
    private int redstoneLimitBanDuration;

    @Config(path = "redstone_limit.notify_interval")
    private int redstoneLimitNotifyInterval;

    @Config(path = "redstone_limit.duration")
    private int redstoneLimitDuration;

    @Config(path = "anti_trample_crop.enabled")
    private boolean antiTrampleCropEnabled;

    @Config(path = "quickshop_bug_fix.enabled")
    private boolean qucikshopBugFixEnabled;

    @Config(path = "entity_spawn_interval_limit.enabled")
    private boolean entitySpawnIntervalLimitEnabled;


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

    private Set<EntityType> cleanEntityFilterTypes = new HashSet<>();
    private Set<Material> cleanDropFilterMaterials = new HashSet<>();
    private Map<String, Map<EntityType, Integer>> antiEntityFarmWorldMap = new HashMap<>();
    private Map<EntityType, Long> entitySpawnIntervalLimitMap = new HashMap<>();

    public Map<EntityType, Long> getEntitySpawnIntervalLimitMap() {
        return entitySpawnIntervalLimitMap;
    }

    public void setEntitySpawnIntervalLimitMap(@NotNull Map<EntityType, Long> entitySpawnIntervalLimitMap) {
        if (entitySpawnIntervalLimitMap.containsKey(null) || entitySpawnIntervalLimitMap.containsValue(null)) {
            throw new NullPointerException();
        }

        this.entitySpawnIntervalLimitMap = new HashMap<>(entitySpawnIntervalLimitMap);
    }

    public boolean isCleanEntityFilterWaterMobs() {
        return cleanEntityFilterWaterMobs;
    }

    public int getCleanDropCountdownSecond() {
        return cleanDropCountdownSecond;
    }

    public int getCleanEntityCountdownSecond() {
        return cleanEntityCountdownSecond;
    }

    public boolean isCleanEntityFilterNamed() {
        return cleanEntityFilterNamed;
    }

    public boolean isCleanEntityFilterNpcs() {
        return cleanEntityFilterNpcs;
    }

    public boolean isCleanEntityFilterGolems() {
        return cleanEntityFilterGolems;
    }

    public int getCleanEntityInterval() {
        return cleanEntityInterval;
    }

    public void setCleanEntityFilterTypes(@NotNull Set<EntityType> cleanEntityFilterTypes) {
        if (cleanEntityFilterTypes.contains(null)) {
            throw new NullPointerException();
        }

        this.cleanEntityFilterTypes = new HashSet<>(cleanEntityFilterTypes);
    }

    public boolean isCleanEntityEnabled() {
        return cleanEntityEnabled;
    }

    public Set<String> getCleanEntityWorlds() {
        return new HashSet<>(cleanEntityWorlds);
    }

    public boolean isCleanEntityFilterAnimals() {
        return cleanEntityFilterAnimals;
    }

    public Set<EntityType> getCleanEntityFilterTypes() {
        return new HashSet<>(cleanEntityFilterTypes);
    }

    public Set<String> getCleanDropWorlds() {
        return new HashSet<>(cleanDropWorlds);
    }

    public void setCleanDropFilterMaterials(@NotNull Set<Material> cleanDropFilterMaterials) {
        if (cleanDropFilterMaterials.contains(null)) {
            throw new NullPointerException();
        }

        this.cleanDropFilterMaterials = new HashSet<>(cleanDropFilterMaterials);
    }

    public boolean isCleanDropFilterLoreItems() {
        return cleanDropFilterLoreItems;
    }

    public Set<Material> getCleanDropFilterMaterials() {
        return new HashSet<>(cleanDropFilterMaterials);
    }

    public int getCleanDropInterval() {
        return cleanDropInterval;
    }

    public int getAntiEntityFarmInterval() {
        return antiEntityFarmInterval;
    }

    public void setAntiEntityFarmWorldMap(@NotNull Map<String, Map<EntityType, Integer>> map) {
        if (map.containsKey(null)) {
            throw new NullPointerException("存在 null key");
        }

        if (map.containsValue(null)) {
            throw new NullPointerException("存在 null value");
        }

        this.antiEntityFarmWorldMap = new HashMap<>(map);
    }

    public Map<String, Map<EntityType, Integer>> getAntiEntityFarmWorldMap() {
        return new HashMap<>(antiEntityFarmWorldMap);
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

    public List<String> getRedstoneLimitWorlds() {
        return new ArrayList<>(redstoneLimitWorlds);
    }
}
