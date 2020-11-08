package com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.objects;

import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.BaseEntityMatcher;
import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.EntityMatcherType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * 匹配正则类型
 */
public class EnumEntityMatcher extends BaseEntityMatcher {
    private final Set<String> regexes;
    private final Set<EntityType> entityTypes = new HashSet<>();

    public EnumEntityMatcher(@NotNull ConfigurationSection section) {
        this(new HashSet<>(section.getStringList("regexes")));
    }

    public EnumEntityMatcher(@NotNull Set<String> regexes) {
        super(EntityMatcherType.ENUM);
        this.regexes = regexes;

        for (EntityType entityType : EntityType.values()) {
            for (String regex : regexes) {
                if (entityType.name().matches(regex)) {
                    this.entityTypes.add(entityType);
                }   
            }
        }
    }

    public Set<String> getRegexes() {
        return new HashSet<>(this.regexes);
    }

    @Override
    public boolean isMatched(@NotNull Entity entity) {
        return this.entityTypes.contains(entity.getType());
    }

    @Override
    public String toString() {
        return "EnumEntityMatcher{" +
                "regexes=" + regexes +
                ", entityTypes=" + entityTypes +
                '}';
    }
}
