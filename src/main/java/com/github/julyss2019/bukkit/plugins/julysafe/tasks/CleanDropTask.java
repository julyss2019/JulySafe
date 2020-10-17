package com.github.julyss2019.bukkit.plugins.julysafe.tasks;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.bossbar.GlobalBossBarManager;
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
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.stream.Collectors;

public class CleanDropTask extends BukkitRunnable {
    private final JulySafe plugin = JulySafe.getInstance();
    private final GlobalBossBarManager globalBossBarManager = plugin.getGlobalBossBarManager();
    private final Logger logger = plugin.getPluginLogger();
    private final MainConfig mainConfig = plugin.getMainConfig();
    private final MainConfigHelper mainConfigHelper = plugin.getMainConfigHelper();
    private final Lang lang = plugin.getLang().getLang("clean_drop");
    private final LangHelper langHelper = plugin.getLangHelper();
    private final BossBar bossBar = Bukkit.createBossBar(null, mainConfig.getCleanDropBossBarColor(), mainConfig.getCleanDropBossBarStyle());
    private int counter = 0;

    @Override
    public void run() {
        counter++;

        int countdown = getCountdown();

        if (countdown == 0) {
            int total = 0;

            for (World world : Bukkit.getWorlds()) {
                if (!mainConfigHelper.isCleanDropWorld(world)) {
                    continue;
                }

                for (Entity entity : world.getEntities().stream().filter(entity -> entity instanceof Item).collect(Collectors.toList())) {
                    Item item = (Item) entity;

                    if (mainConfig.getCleanDropTarget().isTarget(item)) {
                        total += item.getItemStack().getAmount();
                        entity.remove();
                        logger.debug("[clean_drop] [删除掉落物] 物品 = " + item.getItemStack() + ", 位置 = " + entity.getLocation() + ".");
                    }
                }
            }

            JulyMessage.broadcastColoredMessage(langHelper.handleText(lang.getString("finished"), new PlaceholderContainer()
                    .add("count", String.valueOf(total))));
            counter = 0;
            globalBossBarManager.unregisterGlobalBar(bossBar);
            bossBar.removeAll();
            return;
        }

        // 倒计时阶段
        if (mainConfig.getCleanDropCountdownSecond() >= countdown) {
            if (!globalBossBarManager.hasGlobalBar(bossBar)) {
                globalBossBarManager.registerGlobalBar(bossBar);
            }

            bossBar.setTitle(JulyText.getColoredText(langHelper.handleText(lang.getString("countdown_boss_bar"), new PlaceholderContainer()
                    .add("seconds", String.valueOf(countdown)))));
            bossBar.setProgress((double) (getCountdown()) / mainConfig.getCleanDropCountdownSecond());
        }
    }

    public void onDisabled() {
        bossBar.removeAll();
    }

    public int getCountdown() {
        return mainConfig.getCleanDropInterval() - counter;
    }
}
