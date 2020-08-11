package com.github.julyss2019.bukkit.plugins.julysafe.task;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfig;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfigHelper;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.Lang;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangHelper;
import com.github.julyss2019.mcsp.julylibrary.logger.Logger;
import com.github.julyss2019.mcsp.julylibrary.message.JulyMessage;
import com.github.julyss2019.mcsp.julylibrary.text.JulyText;
import com.github.julyss2019.mcsp.julylibrary.text.PlaceholderContainer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

public class CleanEntityTask extends BukkitRunnable {
    private final JulySafe plugin = JulySafe.getInstance();
    private final Logger logger = plugin.getPluginLogger();
    private final MainConfig mainConfig = plugin.getMainConfig();
    private final MainConfigHelper mainConfigHelper = plugin.getMainConfigHelper();
    private final Lang lang = plugin.getLang().getLang("clean_entity");
    private final LangHelper langHelper = plugin.getLangHelper();
    private final BossBar bossBar = Bukkit.createBossBar("", BarColor.YELLOW, BarStyle.SOLID);
    private int counter = 0;

    @Override
    public void run() {
        int countdown = getCountdown();

        if (mainConfig.getCleanEntityCountdownSecond() >= countdown) {
            bossBar.removeAll();
            Bukkit.getOnlinePlayers().forEach(o -> {
                if (!bossBar.getPlayers().contains(o)) {
                    bossBar.addPlayer(o);
                }
            });
            bossBar.setTitle(JulyText.getColoredText(langHelper.handleText(lang.getString("countdown_boss_bar"),
                    new PlaceholderContainer()
                            .add("seconds", String.valueOf(countdown)))));
            bossBar.setProgress((double) (mainConfig.getCleanEntityInterval() - counter) / mainConfig.getCleanEntityCountdownSecond());
        }

        if (countdown == 0) {
            int total = 0;

            for (World world : Bukkit.getWorlds()) {
                if (!mainConfigHelper.isCleanDropWorld(world)) {
                    continue;
                }

                for (LivingEntity livingEntity : world.getLivingEntities()) {
                    EntityType type = livingEntity.getType();

                    // 过滤 ArmorStand 等
                    if (!(livingEntity instanceof Mob)) {
                        continue;
                    }

                    if (livingEntity instanceof Player) {
                        continue;
                    }

                    if (livingEntity.hasMetadata("NPC") || livingEntity.hasMetadata("MyPet")) {
                        continue;
                    }

                    if (mainConfig.isCleanEntityFilterNamed() && livingEntity.getCustomName() != null) {
                        continue;
                    }

                    if (mainConfig.getCleanEntityFilterTypes().contains(type)) {
                        continue;
                    }

                    if (mainConfig.isCleanEntityFilterGolems() && livingEntity instanceof Golem) {
                        continue;
                    }

                    if (mainConfig.isCleanEntityFilterNpcs() && livingEntity instanceof NPC) {
                        continue;
                    }

                    if (mainConfig.isCleanEntityFilterAnimals() && livingEntity instanceof Animals) {
                        continue;
                    }

                    if (mainConfig.isCleanEntityFilterWaterMobs() && livingEntity instanceof WaterMob) {
                        continue;
                    }

                    total += 1;
                    logger.debug("[clean_entity] [删除实体] 实体 = " + type.name() + ", 位置 = " + livingEntity.getLocation() + ".");
                    livingEntity.remove();
                }
            }

            bossBar.removeAll();
            JulyMessage.broadcastColoredMessage(JulyText.setPlaceholders(lang.getString("finished"), new PlaceholderContainer()
                    .add("count", String.valueOf(total))));
            counter = 0;
        }

        counter++;
    }

    public void onDisabled() {
        bossBar.removeAll();
    }

    public int getCountdown() {
        return mainConfig.getCleanEntityInterval() - counter;
    }
}
