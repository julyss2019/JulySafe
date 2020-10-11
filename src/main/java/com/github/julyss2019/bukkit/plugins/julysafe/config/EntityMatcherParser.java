package com.github.julyss2019.bukkit.plugins.julysafe.config;

import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.EntityMatcher;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.EntityMatcherType;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class EntityMatcherParser {
    public static EntityMatcher parse(@NotNull ConfigurationSection section) {
        EntityMatcherType entityMatcherType = EntityMatcherType.valueOf(Optional.ofNullable(section.getString("type")).orElseThrow(() -> new RuntimeException(section + " must contains type key.")));
        Class<? extends EntityMatcher> matcherClass = entityMatcherType.getMatcherClass();
        EntityMatcher entityMatcher;

        try {
            entityMatcher = matcherClass.getConstructor(ConfigurationSection.class).newInstance(section);
        } catch (Exception e) {
            throw new RuntimeException("create drop matcher failed", e);
        }

        return entityMatcher;
    }
}
