package com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface EntityMatcher {
    boolean isMatched(@NotNull Entity entity);
    EntityMatcherType getType();
}
