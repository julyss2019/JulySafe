package com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop;

import org.jetbrains.annotations.NotNull;

public abstract class BaseDropMatcher implements DropMatcher {
    private DropMatcherType type;

    public BaseDropMatcher(@NotNull DropMatcherType type) {
        this.type = type;
    }

    @Override
    public DropMatcherType getType() {
        return type;
    }
}
