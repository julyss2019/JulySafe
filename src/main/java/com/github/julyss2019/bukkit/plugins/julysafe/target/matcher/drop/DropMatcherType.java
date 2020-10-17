package com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop;

import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.objects.CustomNameDropMatcher;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.objects.EnchantmentDropMatcher;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.objects.EnumDropMatcher;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.drop.objects.LoreDropMatcher;
import org.jetbrains.annotations.NotNull;

public enum DropMatcherType {
    ENUM(EnumDropMatcher.class)
    , ENCHANTMENT(EnchantmentDropMatcher.class)
    , CUSTOM_NAME(CustomNameDropMatcher.class)
    , LORE(LoreDropMatcher.class);

    private final Class<? extends DropMatcher> matcherClass;

    DropMatcherType(@NotNull Class<? extends DropMatcher> matcherClass) {
        this.matcherClass = matcherClass;
    }

    public Class<? extends DropMatcher> getMatcherClass() {
        return matcherClass;
    }
}
