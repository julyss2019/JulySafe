package com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity;

import org.jetbrains.annotations.NotNull;

public abstract class BaseEntityMatcher implements EntityMatcher {
    private EntityMatcherType type;

    public BaseEntityMatcher(@NotNull EntityMatcherType type) {
        this.type = type;
    }

    @Override
    public EntityMatcherType getType() {
        return type;
    }
}
