package com.github.julyss2019.bukkit.plugins.julysafe.config;

import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.DropMatcher;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.DropMatcherType;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DropMatcherParser {
    public static DropMatcher parse(@NotNull ConfigurationSection section) {
        DropMatcherType dropMatcherType = DropMatcherType.valueOf(Optional.ofNullable(section.getString("type")).orElseThrow(() -> new RuntimeException(section + " must contains type key.")));
        Class<? extends DropMatcher> matcherClass = dropMatcherType.getMatcherClass();
        DropMatcher dropMatcher;

        try {
            dropMatcher = matcherClass.getConstructor(ConfigurationSection.class).newInstance(section);
        } catch (Exception e) {
            throw new RuntimeException("create drop matcher failed", e);
        }

        return dropMatcher;
    }
}
