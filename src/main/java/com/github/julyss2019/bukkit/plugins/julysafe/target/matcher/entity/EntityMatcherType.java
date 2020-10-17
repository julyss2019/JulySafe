package com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity;

import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.objects.ClassEntityMatcher;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.objects.EnumEntityMatcher;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.objects.MetadataEntityMatcher;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.objects.CustomNameEntityMatcher;
import org.jetbrains.annotations.NotNull;

public enum EntityMatcherType {
    CLASS(ClassEntityMatcher.class)
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
