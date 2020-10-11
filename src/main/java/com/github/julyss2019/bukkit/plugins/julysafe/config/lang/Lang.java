package com.github.julyss2019.bukkit.plugins.julysafe.config.lang;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public class Lang {
    private final ConfigurationSection currentSection;

    public Lang(@NotNull ConfigurationSection currentSection) {
        this.currentSection = currentSection;
    }

    public String getString(@NotNull String path) {
        return currentSection.getString(path);
    }

    public Lang getLang(@NotNull String path) {
        return new Lang(currentSection.getConfigurationSection(path));
    }
}
