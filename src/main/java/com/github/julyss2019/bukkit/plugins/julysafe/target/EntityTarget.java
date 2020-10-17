package com.github.julyss2019.bukkit.plugins.julysafe.target;

import com.github.julyss2019.bukkit.plugins.julysafe.target.matcher.entity.EntityMatcher;
import com.github.julyss2019.mcsp.julylibrary.utilv2.ValidateUtil;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class EntityTarget {
    private final Set<EntityMatcher> includes;
    private final Set<EntityMatcher> excludes;

    public EntityTarget(@NotNull Set<EntityMatcher> includes, @NotNull Set<EntityMatcher> excludes) {
        this.includes = new HashSet<>(ValidateUtil.notNullElement(includes));
        this.excludes = new HashSet<>(ValidateUtil.notNullElement(excludes));
    }

    public Set<EntityMatcher> getIncludes() {
        return new HashSet<>(includes);
    }

    public Set<EntityMatcher> getExcludes() {
        return new HashSet<>(excludes);
    }

    public boolean isTarget(@NotNull Entity entity) {
        for (EntityMatcher entityMatcher : excludes) {
            if (entityMatcher.isMatched(entity)) {
                return false;
            }
        }

        for (EntityMatcher entityMatcher : includes) {
            if (entityMatcher.isMatched(entity)) {
                return true;
            }
        }

        return false;
    }
}
