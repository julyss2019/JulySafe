package com.github.julyss2019.bukkit.plugins.julysafe.listener;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfig;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfigHelper;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.Lang;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangHelper;
import com.github.julyss2019.mcsp.julylibrary.logger.Logger;
import com.github.julyss2019.mcsp.julylibrary.text.JulyText;
import com.github.julyss2019.mcsp.julylibrary.text.PlaceholderContainer;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class RedstoneLimitListener implements Listener {
    public static class Counter {
        private final JulySafe plugin = JulySafe.getInstance();
        private final Chunk chunk;
        private long beginningTime;
        private long lastRedstone;
        private int count;

        public Counter(@NotNull Chunk chunk) {
            this.chunk = chunk;
        }

        public Chunk getChunk() {
            return chunk;
        }

        public long getBeginningTime() {
            return beginningTime;
        }

        public void setBeginningTime(long beginningTime) {
            this.beginningTime = beginningTime;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public long getLastRedstone() {
            return lastRedstone;
        }

        public void setLastRedstone(long lastRedstone) {
            this.lastRedstone = lastRedstone;
        }

        public void update() {
            if (System.currentTimeMillis() - getBeginningTime() > plugin.getMainConfig().getRedstoneLimitDuration() * 1000L) {
                setCount(0);
                setBeginningTime(System.currentTimeMillis());
            }
        }

        public void reset() {
            setCount(0);
            setBeginningTime(System.currentTimeMillis());
        }
    }

    private final JulySafe plugin = JulySafe.getInstance();
    private final Logger logger = plugin.getPluginLogger();
    private final MainConfig mainConfig = plugin.getMainConfig();
    private final MainConfigHelper mainConfigHelper = plugin.getMainConfigHelper();
    private final Lang lang = plugin.getLang().getLang("redstone_limit");
    private final LangHelper langHelper = plugin.getLangHelper();
    private final SimpleDateFormat SDF = new SimpleDateFormat(lang.getString("date_time_format"));

    private final Map<Chunk, Counter> counterMap = new HashMap<>();
    private final Map<Chunk, Long> banMap = new HashMap<>();
    private final Map<UUID, Long> notifyMap = new HashMap<>();

    private Counter getCounter(@NotNull Chunk chunk) {
        if (!counterMap.containsKey(chunk)) {
            Counter counter = new Counter(chunk);

            counter.setBeginningTime(System.currentTimeMillis());
            counterMap.put(chunk, counter);
            return counter;
        } else {
            counterMap.get(chunk).update();
        }

        return counterMap.get(chunk);
    }

    private boolean isBannedChunk(@NotNull Chunk chunk) {
        return System.currentTimeMillis() < banMap.getOrDefault(chunk, -1L);
    }

    private void banChunk(@NotNull Chunk chunk) {
        banMap.put(chunk, System.currentTimeMillis() + mainConfig.getRedstoneLimitBanDuration() * 1000L);
    }

    @EventHandler
    public void onBlockRedstoneEvent(BlockRedstoneEvent event) {
        if (!mainConfigHelper.isRedstoneLimitWorld(event.getBlock().getWorld())) {
            return;
        }

        if (plugin.getAverageTps() > mainConfig.getRedstoneLimitTps()) {
            return;
        }

        Block block = event.getBlock();
        Location location = block.getLocation();
        Chunk chunk = block.getChunk();
        Counter counter = getCounter(chunk);

        if (!isBannedChunk(chunk)) {
            if (counter.getLastRedstone() / 1000L != System.currentTimeMillis() / 1000L) {
                counter.setCount(counter.getCount() + 1);
                counter.setLastRedstone(System.currentTimeMillis());

                if (counter.getCount() == mainConfig.getRedstoneLimitThreshold()) {
                    banChunk(chunk);
                    logger.info("[redstone_limit] [禁用红石] 位置 = " + location + ", 附近玩家 = "
                            + Arrays.stream(chunk.getEntities())
                            .filter(entity -> entity instanceof Player)
                            .map(Entity::getName).collect(Collectors.toSet()) + ".");
                    counter.reset();
                }
            }

            return;
        }

        long banExpired = banMap.getOrDefault(chunk, -1L);

        if (System.currentTimeMillis() < banExpired) {

            event.setNewCurrent(0);

            for (Entity entity : chunk.getEntities()) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    UUID uuid = player.getUniqueId();

                    if (System.currentTimeMillis() - notifyMap.getOrDefault(uuid, -1L) > mainConfig.getRedstoneLimitNotifyInterval() * 1000L) {
                        langHelper.sendMsg(player
                                , JulyText.setPlaceholders(lang.getString("redstone_limit.deny"), new PlaceholderContainer()
                                        .add("x", String.valueOf(location.getBlockX()))
                                        .add("y", String.valueOf(location.getBlockY()))
                                        .add("z", String.valueOf(location.getBlockZ()))
                                        .add("expired", SDF.format(banExpired))
                                        .add("threshold", String.valueOf(mainConfig.getRedstoneLimitThreshold()))));
                        notifyMap.put(uuid, System.currentTimeMillis());
                    }
                }
            }
        }
    }
}
