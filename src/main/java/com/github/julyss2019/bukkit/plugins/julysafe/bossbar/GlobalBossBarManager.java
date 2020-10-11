package com.github.julyss2019.bukkit.plugins.julysafe.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class GlobalBossBarManager {
    private final Set<BossBar> globalBars = new HashSet<>();

    public void registerGlobalBar(@NotNull BossBar bossBar) {
        if (globalBars.contains(bossBar)) {
            throw new RuntimeException("已注册");
        }

        Bukkit.getOnlinePlayers().forEach(bossBar::addPlayer);
        globalBars.add(bossBar);
    }

    public void unregisterGlobalBar(@NotNull BossBar bossBar) {
        if (!globalBars.contains(bossBar)) {
            throw new RuntimeException("未注册");
        }

        globalBars.remove(bossBar);
    }

    public boolean hasGlobalBar(@NotNull BossBar bossBar) {
        return globalBars.contains(bossBar);
    }

    public Set<BossBar> getGlobalBars() {
        return new HashSet<>(globalBars);
    }
}
