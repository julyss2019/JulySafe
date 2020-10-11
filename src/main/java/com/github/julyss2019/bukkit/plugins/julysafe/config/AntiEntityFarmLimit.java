package com.github.julyss2019.bukkit.plugins.julysafe.config;

import com.github.julyss2019.bukkit.plugins.julysafe.target.EntityTarget;
import org.jetbrains.annotations.NotNull;

public class AntiEntityFarmLimit {
    private EntityTarget targets;
    private int threshold;

    public AntiEntityFarmLimit(@NotNull EntityTarget targets, int threshold) {
        this.targets = targets;
        this.threshold = threshold;
    }

    public EntityTarget getTarget() {
        return targets;
    }

    public int getThreshold() {
        return threshold;
    }
}
