package com.github.julyss2019.bukkit.plugins.julysafe.config;

import com.github.julyss2019.bukkit.plugins.julysafe.target.EntityTarget;
import org.jetbrains.annotations.NotNull;

public class AntiEntityFarmLimit {
    private final EntityTarget target;
    private final int threshold;

    public AntiEntityFarmLimit(@NotNull EntityTarget target, int threshold) {
        this.target = target;
        this.threshold = threshold;
    }

    public EntityTarget getTarget() {
        return target;
    }

    public int getThreshold() {
        return threshold;
    }
}
