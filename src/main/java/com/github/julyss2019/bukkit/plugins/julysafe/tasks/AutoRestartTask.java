package com.github.julyss2019.bukkit.plugins.julysafe.tasks;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.bossbar.GlobalBossBarManager;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfig;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.Lang;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangHelper;
import com.github.julyss2019.mcsp.julylibrary.logger.Logger;
import com.github.julyss2019.mcsp.julylibrary.text.JulyText;
import com.github.julyss2019.mcsp.julylibrary.text.PlaceholderContainer;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AutoRestartTask extends BukkitRunnable {
    private final JulySafe plug = JulySafe.getInstance();
    private final MainConfig mainConfig = JulySafe.getInstance().getMainConfig();
    private final BossBar bossBar = Bukkit.createBossBar(null
            , mainConfig.getAutoRestartBossBarColor(), mainConfig.getAutoRestartBossBarStyle());
    private final GlobalBossBarManager globalBossBarManager = plug.getGlobalBossBarManager();
    private final Logger logger = plug.getPluginLogger();
    private final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final Lang lang = plug.getLang().getLang("auto_restart");
    private final LangHelper langHelper = plug.getLangHelper();

    @Override
    public void run() {
        Calendar calendar = Calendar.getInstance();
        int nowSeconds = calendar.get(Calendar.SECOND)
                + calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60
                + calendar.get(Calendar.MINUTE) * 60;

        for (int timeSeconds : mainConfig.getAutoRestartTimesSeconds()) {
            if (nowSeconds > timeSeconds) {
                return;
            }

            if (nowSeconds == timeSeconds) {
                logger.info("[auto_restart] [restart] now = " + SDF.format(new Date()));
                mainConfig.getAutoRestartBeforeCommands().forEach(s -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s));
                globalBossBarManager.unregisterGlobalBar(bossBar);
                Bukkit.getServer().shutdown();
                return;
            }

            int countdown = timeSeconds - nowSeconds;

            if (countdown <= mainConfig.getAutoRestartCountdownSeconds()) {
                if (!globalBossBarManager.hasGlobalBar(bossBar)) {
                    globalBossBarManager.registerGlobalBar(bossBar);
                }

                bossBar.setTitle(JulyText.getColoredText(langHelper.handleText(lang.getString("countdown_boss_bar"), new PlaceholderContainer()
                        .add("seconds", String.valueOf(countdown)))));
                bossBar.setProgress((timeSeconds - nowSeconds) / (double) mainConfig.getAutoRestartCountdownSeconds());
            }
        }
    }
}
