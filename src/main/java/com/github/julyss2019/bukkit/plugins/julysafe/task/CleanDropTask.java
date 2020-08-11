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
import com.github.julyss2019.mcsp.julylibrary.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.stream.Collectors;

public class CleanDropTask extends BukkitRunnable {
    private final JulySafe plugin = JulySafe.getInstance();
    private final Logger logger = plugin.getPluginLogger();
    private final MainConfig mainConfig = plugin.getMainConfig();
    private final MainConfigHelper mainConfigHelper = plugin.getMainConfigHelper();
    private final Lang lang = plugin.getLang().getLang("clean_drop");
    private final LangHelper langHelper = plugin.getLangHelper();
    private final BossBar bossBar = Bukkit.createBossBar("", BarColor.YELLOW, BarStyle.SOLID);
    private int counter = 0;

    @Override
    public void run() {
        int countdown = getCountdown();

        if (mainConfig.getCleanDropCountdownSecond() >= countdown) {
            bossBar.removeAll();
            Bukkit.getOnlinePlayers().forEach(o -> {
                if (!bossBar.getPlayers().contains(o)) {
                    bossBar.addPlayer(o);
                }
            });
            bossBar.setTitle(JulyText.getColoredText(langHelper.handleText(lang.getString("countdown_boss_bar"), new PlaceholderContainer()
                    .add("seconds", String.valueOf(countdown)))));
            bossBar.setProgress((double) (mainConfig.getCleanDropInterval() - counter) / mainConfig.getCleanDropCountdownSecond());
        }

        if (countdown == 0) {
            int total = 0;

            for (World world : Bukkit.getWorlds()) {
                if (!mainConfigHelper.isCleanDropWorld(world)) {
                    continue;
                }

                for (Entity entity : world.getEntities().stream().filter(entity -> entity instanceof Item).collect(Collectors.toList())) {
                    ItemStack itemStack = ((Item) entity).getItemStack();

                    if (mainConfig.getCleanDropFilterMaterials().contains(itemStack.getType())) {
                        continue;
                    }

                    if (mainConfig.isCleanDropFilterLoreItems() && !ItemUtil.getLores(itemStack).isEmpty()) {
                        continue;
                    }

                    total += itemStack.getAmount();
                    entity.remove();
                    logger.debug("[clean_drop] [删除掉落物] 物品 = " + itemStack + ", 位置 = " + entity.getLocation() + ".");
                }
            }

            bossBar.removeAll();
            JulyMessage.broadcastColoredMessage(langHelper.handleText(lang.getString("finished"), new PlaceholderContainer()
                    .add("count", String.valueOf(total))));
            counter = 0;
        }

        counter++;
    }

    public void onDisabled() {
        bossBar.removeAll();
    }

    public int getCountdown() {
        return mainConfig.getCleanDropInterval() - counter;
    }
}
