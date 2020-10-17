package com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop;

import org.bukkit.entity.Item;
import org.jetbrains.annotations.NotNull;

public interface DropMatcher {
    boolean isMatched(@NotNull Item item);
    DropMatcherType getType();
}
