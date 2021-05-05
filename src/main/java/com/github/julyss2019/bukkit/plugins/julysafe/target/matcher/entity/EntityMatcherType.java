package com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity;

import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.objects.*;
import org.jetbrains.annotations.NotNull;

public enum EntityMatcherType {
    CLASS(ClassEntityMatcher.class)
    , NAME(NameEntityMatcher.class)
    , ENUM(EnumEntityMatcher.class)
    , METADATA(MetadataEntityMatcher.class)
    , CUSTOM_NAME(CustomNameEntityMatcher.class);

    private final Class<? extends EntityMatcher> matcherClass;

    EntityMatcherType(@NotNull Class<? extends EntityMatcher> matcherClass) {
        this.matcherClass = matcherClass;
    }

    public Class<? extends EntityMatcher> getMatcherClass() {
        return matcherClass;
    }
}
